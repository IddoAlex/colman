package view;

import java.io.InputStream;
import java.io.OutputStream;

public interface MVPView {

	public void setInputStream(InputStream input);
	
	public void setOutputStream(OutputStream output);
 
	public void display(IDisplayable displayable);
	
	public void start();
}
