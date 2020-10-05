package App;

import android.os.Build;
import android.renderscript.ScriptGroup;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.*;

import java.io.IOException;
import java.net.UnknownHostException;

public class DBCommunication
{
    String clientName = "";
    String hostName = "";

    public DBCommunication()
    {
        clientName = "Kay"
        hostName = "https://databases-auth.000webhost.com/sql.php?server=1&db=id14979143_appdb&table=Households&pos=0";
    }


}
