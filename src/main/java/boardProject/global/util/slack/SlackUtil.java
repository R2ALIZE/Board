package boardProject.global.util.slack;

import boardProject.global.util.time.TimeUtil;
import com.slack.api.Slack;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import com.slack.api.webhook.WebhookPayloads;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;


/*
    추가 구현 사항:
    Business Exception일 경우에는 메세지 보내지 않고 Internal Server 문제일 때만 메세지 보내기

 */

@Component
@NoArgsConstructor
public class SlackUtil {

    @Value("${slack.webhook.url}")
    private String SLACK_WEBHOOK_URL;

    private StringBuilder sb = new StringBuilder();



    public void sendAlert(Exception e, HttpServletRequest request) throws IOException {

        List<LayoutBlock> layoutBlocks = createLayoutBlock(e,request);

        Slack.getInstance().send(SLACK_WEBHOOK_URL, WebhookPayloads
                .payload(p->
                        p.username("EXCEPTION DETECTED 🚨")
                                .iconUrl("<https://yt3.googleusercontent.com/ytc/AGIKgqMVUzRrhoo1gDQcqvPo0PxaJz7e0gqDXT0D78R5VQ=s900-c-k-c0x00ffffff-no-rj>")
                                .blocks(layoutBlocks)));

    }


    private List<LayoutBlock> createLayoutBlock (Exception e, HttpServletRequest request) {

        return Blocks.asBlocks(
                getMessageHeader("서버 측 오류로 예상되는 예외 상황이 발생하였습니다."),
                Blocks.divider(),
                getSection(createErrorMessage(e)),
                Blocks.divider(),
                getSection(createErrorSpotMessage(request)),
                Blocks.divider(),
                getSection("<https://github.com/R2ALIZE/Board/issues|해당 문제에 대해 Github Issue 생성하러가기>")
        );

    }

    private String createErrorMessage(Exception e) {

        sb.setLength(0);
        sb.append("*[🔥 Exception]*");
        sb.append("\n");
        sb.append(e.toString());
        sb.append("\n");
        sb.append("\n");
        sb.append("*[📩 From]*");
        sb.append("\n");
        sb.append(readRootStackTrace(e));
        sb.append("\n");
        sb.append("\n");

        return sb.toString();

    }


    private String createErrorSpotMessage(HttpServletRequest request) {

        sb.setLength(0);
        sb.append("*[🧾세부정보]*");
        sb.append("\n");
        sb.append("Request URL : " + request.getRequestURL().toString());
        sb.append("\n");
        sb.append("Request Method : " + request.getMethod());
        sb.append("\n");
        sb.append("Request Time : " + TimeUtil.getNowAsUtcZero());
        sb.append("\n");

        return sb.toString();

    }

    private String readRootStackTrace(Exception e) {
        return e.getStackTrace()[0].toString();
    }

    private LayoutBlock getMessageHeader(String text) {
        return Blocks.header(header -> header.text(
                plainText(pt -> pt.emoji(true).text(text))
                )
        );
    }

    private LayoutBlock getSection(String message) {
        return Blocks.section(s -> s.text(BlockCompositions.markdownText(message)));
    }

}
