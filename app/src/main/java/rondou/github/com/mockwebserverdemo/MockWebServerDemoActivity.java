package rondou.github.com.mockwebserverdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.AsyncTask;
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

    private MockWebServer mServer = new MockWebServer();
    private Button mMockStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_web_server_demo);

        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                if (request.getPath().equals("/hellomockserver")){
                    return new MockResponse().setResponseCode(200).setBody("hellomockserver");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mServer.setDispatcher(dispatcher);

        mMockStartButton = (Button)findViewById(R.id.mockserverbutton);
        mMockStartButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    new TestHttpsRequest().execute();
                } catch (Throwable e) {
                    Log.d("MockWebServerDemo", "TestHttpsRequestException = " + e);
                }
            }

        });
    }

    private class TestHttpsRequest extends AsyncTask<Void, Void, Void> {
        @Override protected Void doInBackground(Void... params) {
            try {
                startWebServer();
            } catch (Exception e) {
                Log.d("MockWebServerDemo", "startWebServerException = " + e);
                //throw new AssertionError(e);
            }
            return null;
        }

    }

    private void startWebServer() throws Exception {
        mServer.start();
        connectionTest();
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
        HttpURLConnection urlConnection = null;
        URL url = mServer.getUrl("/hellomockserver");
        String result = null;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            result = out.toString();

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        Log.d("MockWebServerDemo", "testConnectresult = " + result);
    }
}
