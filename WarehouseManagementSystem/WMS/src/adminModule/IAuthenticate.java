package adminModule;

import java.io.IOException;

public interface IAuthenticate {
    
    public boolean authenticate(String username, String password) throws IOException ;

}
