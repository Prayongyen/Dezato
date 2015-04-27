package th.ac.buu.se.s55160077.s55160018.dezato;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RestService {
    public JSONObject AddFoodOrder(String url,OrderItem item)
    {
        InputStream inputStream = null;
        boolean resultBool = false;
        JSONObject jsonResult = null;

        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL

            HttpPost httpPut = new
                    HttpPost(url);
            String json = "";
            //              // 3. build jsonObject
            //              JSONObject jsonObject2 = new JSONObject();
            //              jsonObject2.put("idGuarderias", idG);
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("order_no",item.getOrder_no());
            jsonObject.put("order_qty",item.getOrder_qty());
            jsonObject.put("table_id",item.getTable_id());
            jsonObject.put("food_id",item.getFood_id());
            //sonObject.put("guarderiasIdGuarderias",jsonObject2);
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPut.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);
            //Try to add this
            inputStream = httpResponse.getEntity().getContent();


            String result= convertStreamToString(inputStream);
            // construct a JSON object with result
            jsonResult =new JSONObject(result);
            Log.d("JSON",jsonResult.getString("message"));
            // Closing the input stream will trigger connection release
            inputStream.close();

        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }
        return jsonResult;
    }
    public JSONObject putTableReserve(String url,ReserveItem item)
    {
        InputStream inputStream = null;
        boolean resultBool = false;
        JSONObject jsonResult = null;

        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL

            HttpPost httpPut = new
                    HttpPost(url);
            String json = "";
            //              // 3. build jsonObject
            //              JSONObject jsonObject2 = new JSONObject();
            //              jsonObject2.put("idGuarderias", idG);
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("table_id",item.getTxtTableNo());
            jsonObject.put("res_cusname",item.getTxtCustomer());
            jsonObject.put("res_cusphone",item.getTxtTel());
            jsonObject.put("res_time",item.getTxtTime());
            //sonObject.put("guarderiasIdGuarderias",jsonObject2);
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPut.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);
            //Try to add this
            inputStream = httpResponse.getEntity().getContent();


            String result= convertStreamToString(inputStream);
            // construct a JSON object with result
            jsonResult =new JSONObject(result);
            Log.d("JSON",jsonResult.getString("message"));
            // Closing the input stream will trigger connection release
            inputStream.close();

        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }
        return jsonResult;
    }
    public JSONObject putTableEating(String url,TableItem item)
    {
        InputStream inputStream = null;
        boolean resultBool = false;
        JSONObject jsonResult = null;

        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL

            HttpPost httpPut = new
                    HttpPost(url);
            String json = "";
            //              // 3. build jsonObject
            //              JSONObject jsonObject2 = new JSONObject();
            //              jsonObject2.put("idGuarderias", idG);
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("txtTableStatus",item.getTxtTableStatus());
            jsonObject.put("txtTableNo",item.getTxtTableNo());
            //sonObject.put("guarderiasIdGuarderias",jsonObject2);
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPut.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);
            //Try to add this
            inputStream = httpResponse.getEntity().getContent();


            String result= convertStreamToString(inputStream);
            // construct a JSON object with result
             jsonResult =new JSONObject(result);

            // Closing the input stream will trigger connection release
            inputStream.close();

        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }
        return jsonResult;
    }
    public String putOrder(int table,int foodId,int count)
    {
        Log.d("HEAD", "HELLO");
        InputStream inputStream = null;
        String result = "";
        boolean resultBool = false;

        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL

            HttpPost httpPut = new
                    HttpPost("http://10.20.64.83/dezatoshop/rest/index.php/api/c_dz_order/order");
            String json = "";
            //              // 3. build jsonObject
            //              JSONObject jsonObject2 = new JSONObject();
            //              jsonObject2.put("idGuarderias", idG);
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("order_no",1);
            jsonObject.put("table_id",table);
            jsonObject.put("food_id",foodId);
            jsonObject.put("order_qty",count);
            //      jsonObject.put("guarderiasIdGuarderias",jsonObject2);
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPut.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);


            //Try to add this
            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);/*
                JSONObject jsonResult = new JSONObject(result);    // Converts the string "result" to a JSONObject
                String json_result = jsonResult.getString("message"); // Get the string "result" inside the Json-object
                Log.d("HEAD",json_result.toString());
                if (json_result.equalsIgnoreCase("ADDED")) { // Checks if the "result"-string is equals to "ok"
                    // Result is "OK"
                    resultBool = true;
                    //int customer_id = json.getInt("customer_id"); // Get the int customer_id
                    //String customer_email = json.getString("customer_email"); // I don't need to explain this one, right?

                } else {
                    // Result is NOT "OK"
                }*/
            }
            else {
                result = "Did not work!";
            }

        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }
        Log.d("HEAD",result);
        return result;
    }

    public static JSONObject doGet(String url) {
        JSONObject json = null;

        HttpClient httpclient = new DefaultHttpClient();
        // Prepare a request object
        HttpGet httpget = new HttpGet(url);
        // Accept JSON
        httpget.addHeader("accept", "application/json");
        // Execute the request
        HttpResponse response;

        try {
            response = httpclient.execute(httpget);
            // Get the response entity
            // Log.e("myApp", "Issue is here...!");
            HttpEntity entity = response.getEntity();
            // If response entity is not null
            if (entity != null) {
                // get entity contents and convert it to string
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                // construct a JSON object with result
                json=new JSONObject(result);
                // Closing the input stream will trigger connection release
                instream.close();
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Return the json
        return json;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public JSONObject putLogin(String url,String user,String pw)
    {
        InputStream inputStream = null;
        JSONObject jsonObj = null;
        boolean resultBool = false;

        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL

            HttpPost httpPut = new HttpPost(url);
            String json = "";
            //              // 3. build jsonObject
            //              JSONObject jsonObject2 = new JSONObject();
            //              jsonObject2.put("idGuarderias", idG);
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("user_name",user);
            jsonObject.put("user_password",pw);
            //      jsonObject.put("guarderiasIdGuarderias",jsonObject2);
            json = jsonObject.toString();
            Log.d("STRING",json);
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPut.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);


            //Try to add this
            inputStream = httpResponse.getEntity().getContent();
            String result= convertStreamToString(inputStream);
            // construct a JSON object with result
            jsonObj =new JSONObject(result);
            // Closing the input stream will trigger connection release
            inputStream.close();


        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }
        return jsonObj;
    }

    public JSONObject getOrderbyTableBill(String url,String table_no,String order_no)
    {
        InputStream inputStream = null;
        boolean resultBool = false;
        JSONObject jsonResult = null;

        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL

            HttpPost httpPut = new
                    HttpPost(url);
            String json = "";
            //              // 3. build jsonObject
            //              JSONObject jsonObject2 = new JSONObject();
            //              jsonObject2.put("idGuarderias", idG);
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("table_no",table_no);
            jsonObject.put("order_no",order_no);
            //sonObject.put("guarderiasIdGuarderias",jsonObject2);
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPut.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);
            //Try to add this
            inputStream = httpResponse.getEntity().getContent();


            String result= convertStreamToString(inputStream);
            // construct a JSON object with result
            jsonResult =new JSONObject(result);

            // Closing the input stream will trigger connection release
            inputStream.close();

        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }
        return jsonResult;
    }

    public JSONObject putCheckBill(String url,String table, String User)
    {
        InputStream inputStream = null;
        JSONObject jsonObj = null;
        boolean resultBool = false;

        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL

            HttpPost httpPut = new HttpPost(url);
            String json = "";
            //              // 3. build jsonObject
            //              JSONObject jsonObject2 = new JSONObject();
            //              jsonObject2.put("idGuarderias", idG);
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("table_no",table);
            jsonObject.put("user_name",User);
            //      jsonObject.put("guarderiasIdGuarderias",jsonObject2);
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPut.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);


            //Try to add this
            inputStream = httpResponse.getEntity().getContent();
            String result= convertStreamToString(inputStream);
            // construct a JSON object with result
            jsonObj =new JSONObject(result);
            // Closing the input stream will trigger connection release
            inputStream.close();


        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
        }
        return jsonObj;
    }
}