package cn.digione.yibaic.shop.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import cn.digione.yibaic.shop.R;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.*;
import java.security.*;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-2-21 Time: 上午10:50
 */
public class Utils {
    public static final class Display {
        // 屏幕的宽高, 单位像素
        public static int screenWidth;
        public static int screenHeight;

        // 屏幕的密度
        public static float density; // 只有五种情况 : 0.75/ 1.0/ 1.5/ 2.0/ 3.0
        public static int densityDpi; // 只有五种情况 : 120/ 160/ 240/ 320/ 480

        // 水平垂直精确密度
        public static float xdpi; // 水平方向上的准确密度, 即每英寸的像素点
        public static float ydpi; // 垂直方向上的准确密度, 即没音村的像素点

        public static void getPixelDisplayMetricsII(Activity context) {
            DisplayMetrics dm = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(dm);
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
            density = dm.density;
            densityDpi = dm.densityDpi;
            xdpi = dm.xdpi;
            ydpi = dm.ydpi;
        }

        public static int px2dp(Activity context, int px) {
            // 设备独立像素 和 分辨率之间转换 : dp = px / density ;
            getPixelDisplayMetricsII(context);
            return (int) (px / density + 0.5);
        }

        public static int dp2px(Activity context, int dp) {
            // 像素 和 设备独立像素 转换公式 : px = dp * densityDpi / 160 , density 是归一化密度;
            getPixelDisplayMetricsII(context);
            return dp * densityDpi / 160;
        }
    }

    public static final class DateTime {

        public static String formatDate(Context context, String s) {
            Time time = new Time();
            time.set(1000L * Long.parseLong(s));
            return time.format(context.getString(R.string.order_date_format));
        }

        public static String formatDateString(Context context, String s) {
            Date date = formatStringToDate(context.getString(R.string.repair_progress_date_format_default), s);
            String s1 = s;
            if (date != null) {
                s1 = (new SimpleDateFormat(context.getString(R.string.repair_progress_date_format))).format(date);
            }
            return s1;
        }

        public static Date formatStringToDate(String s, String s1) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
            Date date;
            try {
                date = simpledateformat.parse(s1);
            } catch (ParseException parseexception) {
                parseexception.printStackTrace();
                return null;
            }
            return date;
        }

        public static String formatTime(Context context, String s) {
            return (new SimpleDateFormat(context.getString(R.string.repair_progress_date_format_default)))
                    .format(new Date(1000L * Long.parseLong(s)));
        }

        public static String formatToday(Context context) {
            return formatDate(context, String.valueOf((new Date()).getTime() / 1000L));
        }

        public static String getDayOfMonth(String s) {
            Time time = new Time();
            time.set(1000L * Long.parseLong(s));
            return String.valueOf(time.monthDay);
        }

        public static String getDayOfYear(int i) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.roll(6, i);
            return simpledateformat.format(calendar.getTime());
        }

        public static String getMonth(String s) {
            Time time = new Time();
            time.set(1000L * Long.parseLong(s));
            return String.valueOf(1 + time.month);
        }

        public static String getWeekOfDate(Context context, String s) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            String as[] = context.getResources().getStringArray(R.array.yibaic_reserve_week);
            Calendar calendar = Calendar.getInstance();
            int i;
            try {
                calendar.setTime(simpledateformat.parse(s));
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
            i = -1 + calendar.get(7);
            if (i < 0) {
                i = 0;
            }
            return as[i];
        }

        private static final int MILLSECONDS = 1000;
    }

    public static final class Money {

        public static String valueOf(double d) {
            int i = (int) d;
            if ((double) i == d) {
                return String.valueOf(i);
            } else {
                return String.valueOf(d);
            }
        }

        /**
         * 分转化为元
         *
         * @param fen
         * @return
         */
        public static String MoneyToYuan(int fen) {
            double yuan = fen / 100d;
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(yuan);
        }

        /**
         * 分转化为元
         *
         * @param fen
         * @return
         */
        public static String MoneyToYuan(long fen) {
            double yuan = fen / 100d;
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(yuan);
        }

        /**
         * 分转化为元
         *
         * @param fen
         * @return
         */
        public static String MoneyToYuan(String fen) {
            if (fen == null || "".equals(fen)) {
                return "0";
            }
            double yuan = Double.parseDouble(fen) / 100d;
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(yuan);
        }
    }

    public static final class PhoneFormat {

        public static String valueOf(String phone) {
            if (isPhone(phone)) {
                phone = phone.substring(0, 3) + "****" + phone.substring(7);
            }
            return phone;
        }

        public static boolean isPhone(String phone) {
            return !TextUtils.isEmpty(phone) &&
            Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(phone).matches();
        }

    }

    public static final class Preference {
        public static ArrayList<String> getAllPreferenceKey(Context context) {
            if (context != null) {
                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null && null != sharedpreferences.getAll()) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    Set<String> keySet = sharedpreferences.getAll().keySet();
                    for (String key : keySet) {
                        arrayList.add(key);
                    }
                    return arrayList;
                }
            }
            return null;

        }

        public static boolean getBooleanPref(Context context, String key, boolean flag) {
            SharedPreferences sharedpreferences;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    return sharedpreferences.getBoolean(key, flag);
                }
            }
            return flag;
        }

        public static long getLongPref(Context context, String key, long l) {
            SharedPreferences sharedpreferences;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    return sharedpreferences.getLong(key, l);
                }
            }
            return l;
        }

        public static int getIntPref(Context context, String key, int l) {
            SharedPreferences sharedpreferences;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    return sharedpreferences.getInt(key, l);
                }
            }
            return l;
        }


        public static String getStringPref(Context context, String key, String defaultStr) {
            SharedPreferences sharedpreferences;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    return sharedpreferences.getString(key, defaultStr);
                }
            }
            return defaultStr;
        }

        public static void getMapPref(Context context, Map<String, String> keyValues, String defaultStr) {
            SharedPreferences sharedpreferences;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    for (String key : keyValues.keySet()) {
                        keyValues.put(key, sharedpreferences.getString(key, defaultStr));
                    }
                }
            }
        }

        public static void removePref(Context context, String key) {
            SharedPreferences sharedpreferences;
            SharedPreferences.Editor editor;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    editor = sharedpreferences.edit();
                    editor.remove(key);
                    editor.commit();
                }
            }
        }

        public static void removeMapPref(Context context, Map<String, String> map) {
            SharedPreferences sharedpreferences;
            SharedPreferences.Editor editor;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    editor = sharedpreferences.edit();
                    for (String key : map.keySet()) {
                        editor.remove(key);
                    }
                    editor.commit();
                }
            }
        }

        public static void setBooleanPref(Context context, String s, boolean flag) {
            SharedPreferences sharedpreferences;
            SharedPreferences.Editor editor;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    editor = sharedpreferences.edit();
                    editor.putBoolean(s, flag);
                    editor.commit();
                }
            }

        }

        public static void setIntPref(Context context, String s, int long1) {
            SharedPreferences sharedpreferences;
            SharedPreferences.Editor editor;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    editor = sharedpreferences.edit();
                    editor.putInt(s, long1);
                    editor.commit();
                }
            }
        }

        public static void setLongPref(Context context, String s, long long1) {
            SharedPreferences sharedpreferences;
            SharedPreferences.Editor editor;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    editor = sharedpreferences.edit();
                    editor.putLong(s, long1);
                    editor.commit();
                }
            }
        }

        public static void setStringPref(Context context, String key, String value) {
            SharedPreferences sharedpreferences;
            SharedPreferences.Editor editor;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    editor = sharedpreferences.edit();
                    editor.putString(key, value);
                    editor.commit();
                }
            }
        }

        public static void setMapPref(Context context, Map<String, String> keyValues) {
            SharedPreferences sharedpreferences;
            SharedPreferences.Editor editor;
            if (context != null) {
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedpreferences != null) {
                    editor = sharedpreferences.edit();
                    for (String key : keyValues.keySet()) {
                        editor.putString(key, keyValues.get(key));
                    }
                    editor.commit();
                }
            }
        }
    }

    public static final class Network {

        public static int getActiveNetworkType(Context context) {
            ConnectivityManager connectivitymanager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivitymanager != null) {
                NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
                if (networkinfo != null) {
                    return networkinfo.getType();
                }
            }
            return -1;
        }

        public static String getWifiSSID(Context context) {
            return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getSSID();
        }

        public static boolean isMobileConnected(Context context) {
            NetworkInfo networkinfo =
                    ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return networkinfo != null && networkinfo.getType() == 0;
        }

        public static boolean isNetWorkConnected(Context context) {
            NetworkInfo networkinfo =
                    ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return networkinfo != null && networkinfo.isConnectedOrConnecting();
        }

        public static boolean isWifiConnected(Context context) {
            NetworkInfo networkinfo =
                    ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return networkinfo != null && networkinfo.getType() == 1;
        }

        // 判断当前是否有网络
        public static boolean isHasNetwork(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return false;
            }
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isAvailable());
        }

        public static String getLocalIpAddress() {
            try {
                String ipv4;
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface networkInterface = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddress = networkInterface.getInetAddresses();
                         enumIpAddress.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddress.nextElement();
                        ipv4 = inetAddress.getHostAddress();
                        Log.d("ip address: " + ipv4);
                        if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4)) {
                            return ipv4;
                        }
                    }
                }
            } catch (SocketException ex) {
                Log.e("WifiPreference IpAddress", ex);
            }
            return null;
        }

        public static String getLocalMacAddress(Context context) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        }
    }

    public static final class SoftInput {

        public static void hide(Context context, IBinder ibinder) {
            InputMethodManager inputmethodmanager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputmethodmanager != null) {
                inputmethodmanager.hideSoftInputFromWindow(ibinder, 0);
            }
        }

        public static void show(Context context, View view) {
            InputMethodManager inputmethodmanager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputmethodmanager != null) {
                inputmethodmanager.showSoftInput(view, 0);
            }
        }

        public SoftInput() {
        }
    }

    public static final class Video {

        public static boolean playVideo(Context context, String s) {
            Intent intent = new Intent("android.intent.action.VIEW");
            try {
                intent.setDataAndType(Uri.parse(s), "video/*");
                context.startActivity(intent);
            } catch (Exception exception) {
                return false;
            }
            return true;
        }
    }

    public static final class System {

        public static boolean getSdCardStatus() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }

        public static void popBackStack(FragmentActivity activity) {
            FragmentManager fManager = activity.getSupportFragmentManager();
            FragmentTransaction ft = fManager.beginTransaction();
            fManager.popBackStack();
            ft.commit();
        }
    }

    public static final class Security {

        public static String sha1(String str) {
            if (str == null || str.length() == 0) {
                return null;
            }

            char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

            try {
                MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
                mdTemp.update(str.getBytes());

                byte[] md = mdTemp.digest();
                char buf[] = new char[md.length * 2];
                int k = 0;
                for (byte byte0 : md) {
                    buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    buf[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(buf);
            } catch (Exception e) {
                return null;
            }
        }

    }

    public static final class Http {

        public static byte[] httpGet(final String url) {
            if (url == null || url.length() == 0) {
                Log.e("httpGet, url is null");
                return null;
            }

            HttpClient httpClient = getNewHttpClient();
            HttpGet httpGet = new HttpGet(url);

            try {
                HttpResponse resp = httpClient.execute(httpGet);
                if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    Log.e("httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
                    return null;
                }

                return EntityUtils.toByteArray(resp.getEntity());

            } catch (Exception e) {
                Log.e("httpGet exception, e = " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        public static byte[] httpPost(String url, String entity) {
            if (url == null || url.length() == 0) {
                Log.e("httpPost, url is null");
                return null;
            }

            HttpClient httpClient = getNewHttpClient();

            HttpPost httpPost = new HttpPost(url);

            try {
                httpPost.setEntity(new StringEntity(entity));
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse resp = httpClient.execute(httpPost);
                if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    Log.e("httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
                    return null;
                }

                return EntityUtils.toByteArray(resp.getEntity());
            } catch (Exception e) {
                Log.e("httpPost exception, e = " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        private static class SSLSocketFactoryEx extends SSLSocketFactory {

            SSLContext sslContext = SSLContext.getInstance("TLS");

            public SSLSocketFactoryEx(KeyStore trustStore)
                    throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
                super(trustStore);

                TrustManager tm = new X509TrustManager() {

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws java.security.cert.CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws java.security.cert.CertificateException {
                    }
                };

                sslContext.init(null, new TrustManager[]{tm}, null);
            }

            @Override
            public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
                    throws IOException, UnknownHostException {
                return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
            }

            @Override
            public Socket createSocket() throws IOException {
                return sslContext.getSocketFactory().createSocket();
            }
        }

        private static HttpClient getNewHttpClient() {
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);

                SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

                return new DefaultHttpClient(ccm, params);
            } catch (Exception e) {
                return new DefaultHttpClient();
            }
        }

    }

    public static SpannableStringBuilder getErrorSpanString(int resId, Context context) {
        String s = context.getString(resId);
        TextAppearanceSpan textSpan = new TextAppearanceSpan(context, R.style.TextAppearance_Notice_Normal);
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(s);
        spanBuilder.setSpan(textSpan, spanBuilder.length() - s.length(), spanBuilder.length(), 33);
        return spanBuilder;
    }
}
