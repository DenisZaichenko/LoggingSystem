package logger.handlers.handlerImpl;

import logger.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FileHandler extends AbstractHandler{
    private String fileName;

    public FileHandler(String fileName, String format, DateFormat dateFormat){
        super(format);
        this.fileName = fileName;
    }

    public FileHandler(String fileName, String format){
        this(fileName, format, new SimpleDateFormat());
    }

    public FileHandler(String fileName) {
        this(fileName,
                String.format("[%s] %s\t%s\t%s\t%s\n", Tag.DATE, Tag.SEVERITY, Tag.SENDER, Tag.CONTENT, Tag.EXCEPTION));
    }

    @Override
    public void write(Message msg){
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(format(msg));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
