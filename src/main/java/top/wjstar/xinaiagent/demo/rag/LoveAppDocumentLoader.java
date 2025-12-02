package top.wjstar.xinaiagent.demo.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取所有markdown文档并转换为 Document 列表
 *
 * @author wxvirus
 */
@Component
@Slf4j
public class LoveAppDocumentLoader {

    private final ResourcePatternResolver resourcePatternResolver;

    LoveAppDocumentLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }


    /**
     * 加载所有 Markdown 文档并转换为 Document 列表
     *
     * @return 包含所有 Markdown 文档内容的 Document 列表
     */
    public List<Document> loadMarkdowns() {
        // 初始化文档列表，用于存储所有解析后的文档
        List<Document> documents = new ArrayList<>();
        try {
            // 从 classpath:document/ 目录下加载所有 .md 文件
            Resource[] resources = resourcePatternResolver.getResources("classpath:document/*.md");

            // 遍历每个 Markdown 文件资源
            for (Resource resource : resources) {
                // 获取文件名
                String fileName = resource.getFilename();
                assert fileName != null;

                // 配置 Markdown 文档读取器
                // - withHorizontalRuleCreateDocument(true): 遇到水平分割线时创建新文档
                // - withIncludeCodeBlock(false): 不包含代码块内容
                // - withIncludeBlockquote(false): 不包含引用块内容
                // - withAdditionalMetadata: 添加文件名作为额外的元数据
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)
                        .withIncludeCodeBlock(false)
                        .withIncludeBlockquote(false)
                        .withAdditionalMetadata("filename", fileName)
                        .build();

                // 创建 Markdown 文档读取器并解析文档
                MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);

                // 将解析后的文档添加到列表中
                documents.addAll(reader.get());
            }
        } catch (IOException e) {
            // 记录文档加载异常
            log.error("Markdown 文档加载失败", e);
        }

        // 返回所有加载的文档
        return documents;
    }
}
