package pojo;

import java.util.List;

public class Content {
    private String type;
    private List<TextContent> content;

    public Content() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TextContent> getContent() {
        return content;
    }

    public void setContent(List<TextContent> content) {
        this.content = content;
    }
}
