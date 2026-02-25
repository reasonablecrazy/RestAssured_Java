package pojo;



public class Fields {
	
	private Project project;
    private String summary;
    private IssueType issuetype;
    private Description description;

    public Fields() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public IssueType getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(IssueType issuetype) {
        this.issuetype = issuetype;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}
