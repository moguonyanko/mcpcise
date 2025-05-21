package practice.mcpcise;

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
     * 標高で降順に並べ替えた山の一覧を返します。
     * @return 山の一覧をJSON形式で返します。
     */
    @Tool(description = "山の一覧を返します。標高で降順にソートされます。")
    public String getMountainList(
            @ToolParam(description = "取得する山の数") int limit,
            @ToolParam(description = "並べ替えの順序。descで降順、ascで昇順") String sortType
    ) {
        var result = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("offset", "0")
                        .queryParam("limit", String.valueOf(limit))
                        .queryParam("sort", "elevation." + sortType)
                        .build())
                .retrieve()
                .body(String.class);

        return result;
    }

}
