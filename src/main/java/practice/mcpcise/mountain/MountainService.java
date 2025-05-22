package practice.mcpcise.mountain;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MountainService {

    private final RestClient restClient;

    public MountainService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://mountix.codemountains.org/api/v1/mountains")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("User-Agent", "MyMountainApiClient/1.0")
                .build();
    }

    /**
     * 山の一覧を取得します。
     * getMountainListをオーバーロードするとClaude Dessktopでエラーとなってしまうためメソッド名を変えています。
     * そもそも完全に同じ処理を行うのでない限り、オーバーロードは避けるべきです。
     * @return 山の一覧をJSON形式で返します。
     */
    @Tool(description = "山の一覧を規定の設定で返します。規定の設定は山を10個、標高で降順に並べて返すものです。")
    public MountainList getDefaultMountainList() {
        return this.getMountainList(10, Collections.emptySet(), "desc", -1, null);
    }

    /**
     * 標高で降順に並べ替えた山の一覧を返します。
     * @return 山の一覧をJSON形式で返します。
     */
    @Tool(description = "山の一覧を返します。標高で降順にソートされます。")
    public MountainList getMountainList(
            @ToolParam(description = "取得する山の数です。") int limit,
            @ToolParam(description = "山が属する都道府県。山梨県と静岡県にまたがる山であれば[\"山梨県\",\"静岡県\"]となる。") Set<String> prefectures,
            @ToolParam(description = "並べ替えの順序。descで降順、ascで昇順を指します。") String sortType,
            @ToolParam(description = "山を絞り込む際の基準となる標高値です。") int elevation,
            @ToolParam(description = "標高で山を絞り込む際の方法を指す演算子です。<、>、=、<=、>=が指定できます。左辺が比較対象のMountainオブジェクト、右辺がelevationパラメータです。") String operator
    ) {
        /**
         * 指定した標高で絞り込んでから特定の数だけ山を抽出することがmountixのAPIでできないため、
         * まずは全ての山を取得してから、JavaのStreamAPIで絞り込むようにしています。
         * 並べ替えの順序が指定されていない場合は降順で取得します。
         */
        var mountainsList = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("sort", 
                                "elevation." + (sortType == null || sortType.isEmpty() ? "desc" : sortType))
                        .build())
                .retrieve()
                .body(MountainList.class);
        
        MountainOperator elevationOperator;
        if (elevation >= 0 && operator != null) {
            elevationOperator = new ElevationOperator(elevation, operator);
        } else {
            elevationOperator = new DefaultMountainOperator();
        }

        var prefecturesFilter = new PrefecturesFilter(prefectures);

        var mountains = mountainsList.mountains().stream()
                .filter(prefecturesFilter::accept)
                .filter(elevationOperator::operate)
                .limit(limit)
                .collect(Collectors.toList());

        return new MountainList(mountains);
    }

}
