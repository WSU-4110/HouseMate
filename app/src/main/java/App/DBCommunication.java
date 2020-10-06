package App;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

class DBCommunication //implements Callable
{
    URL hostName;

    private DBCommunication(URL hostName)
    {
        this.hostName = hostName;
    }

    /*@Override
    public String[] call()
    {
        try
        {

        }
    }*/
}
