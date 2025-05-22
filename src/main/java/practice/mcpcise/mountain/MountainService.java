package practice.mcpcise.mountain;

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
        return this.getMountainList(10, "desc", -1, null);
    }

    /**
     * 標高で降順に並べ替えた山の一覧を返します。
     * @return 山の一覧をJSON形式で返します。
     */
    @Tool(description = "山の一覧を返します。標高で降順にソートされます。")
    public MountainList getMountainList(
            @ToolParam(description = "取得する山の数") int limit,
            @ToolParam(description = "並べ替えの順序。descで降順、ascで昇順") String sortType,
            @ToolParam(description = "山の一覧を絞り込むための標高") int elevation,
            @ToolParam(description = "標高の絞り込み方を指定するための演算子。<、>、=、<=、>=が指定できます。左辺が比較対象のMountain、右辺がelevationパラメータです。") String operator
    ) {
        /**
         * 指定した標高で絞り込んでから特定の数だけ山を抽出することがmountixのAPIでできないため、
         * まずは全ての山を取得してから、JavaのStreamAPIで絞り込むようにしています。
         */
        var mountainsList = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("sort", "elevation." + sortType)
                        .build())
                .retrieve()
                .body(MountainList.class);
        
        MountainOperator elevationOperator;
        if (elevation >= 0 && operator != null) {
            elevationOperator = new ElevationOperator(elevation, operator);
        } else {
            elevationOperator = new DefaultMountainOperator();
        }

        var mountains = mountainsList.mountains().stream()
                .filter(elevationOperator::operate)
                .limit(limit)
                .collect(Collectors.toList());

        return new MountainList(mountains);
    }

}
