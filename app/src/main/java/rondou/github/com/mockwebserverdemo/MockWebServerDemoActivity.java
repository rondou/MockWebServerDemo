package rondou.github.com.mockwebserverdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MockWebServerDemoActivity extends ActionBarActivity {

    //private MockWebServer mServer = new MockWebServer();
    private Button mMockStartButton;
    private Button mConnectTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_web_server_demo);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        /*final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                if (request.getPath().equals("/hellomockserver")){
                    return new MockResponse().setResponseCode(200).setBody("hellomockserver");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mServer.setDispatcher(dispatcher);*/

        /*mServer.enqueue(new MockResponse().setBody("hello, world!"));
        mServer.enqueue(new MockResponse().setBody("sup, bra?"));
        mServer.enqueue(new MockResponse().setBody("yo dog"));
        try {
            mServer.start();
        } catch (Throwable e) {
            Log.d("onCreate", "ccc");
        }*/
        mMockStartButton = (Button)findViewById(R.id.mockserverbutton);
        mMockStartButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    new TestHttpsRequest().execute();
                } catch (Throwable e) {
                    Log.d("MockWebServerDemo", "TestHttpsRequestException");
                }
            }

        });

        mConnectTestButton = (Button)findViewById(R.id.connectTestbutton);
        mConnectTestButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    private class TestHttpsRequest extends AsyncTask<Void, Void, Void> {
        @Override protected Void doInBackground(Void... params) {
            try {
                startWebServer();
            } catch (Exception e) {
                Log.d("MockWebServerDemo", "startWebServerException");
                //throw new AssertionError(e);
            }
            return null;
        }

    }

    private void startWebServer() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("hello, mockserver!"));
        server.enqueue(new MockResponse().setBody("sup, bra?"));
        server.enqueue(new MockResponse().setBody("yo tung"));
        server.start();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mock_web_server_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connectionTest() {
    }
}
