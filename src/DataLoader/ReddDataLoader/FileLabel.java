package DataLoader.ReddDataLoader;

/**
 * Gabriel Takács, Mar 2015
 */
public class FileLabel {

    private String fileName;

    private String label;

    public FileLabel(String fileName, String label) {
        this.fileName = fileName;
        this.label = label;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
