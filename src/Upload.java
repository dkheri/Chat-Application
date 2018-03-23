import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Upload implements Runnable
{

	public byte[] byteArr;

	Message msg;

	public Upload(Message msg)
	{
		this.msg=msg;
		File msgFile=(File)msg.file;

		Path path = Paths.get(msgFile.getAbsolutePath());
            try {
                byteArr = Files.readAllBytes(path);
            } catch (IOException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }
		msg.file=byteArr;

	}




	public void run()
	{

            try {
                Client.obout.writeObject(msg);
            } catch (IOException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }

	}



}