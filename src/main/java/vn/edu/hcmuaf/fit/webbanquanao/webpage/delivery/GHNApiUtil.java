package vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GHNApiUtil {
    private static final String GHN_API_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
    private static final String TOKEN = "YOUR_TOKEN";
    private static final int SHOP_ID = 196732;

    public static ShippingFeeResponse calculateShippingFee(ShippingFeeRequest req) throws IOException {
        URL url = new URL(GHN_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Token", TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Tạo payload JSON
        JSONObject payload = new JSONObject();
        payload.put("from_district_id", req.getFromDistrictId());
        payload.put("from_ward_code", req.getFromWardCode());
        payload.put("to_district_id", req.getToDistrictId());
        payload.put("to_ward_code", req.getToWardCode());
        payload.put("weight", req.getWeight());
        payload.put("length", req.getLength());
        payload.put("width", req.getWidth());
        payload.put("height", req.getHeight());
        payload.put("service_id", req.getServiceId());
        payload.put("insurance_value", req.getInsuranceValue());
        payload.put("coupon", req.getCoupon());
        payload.put("shop_id", SHOP_ID);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.toString().getBytes("UTF-8"));
        }

        // Đọc phản hồi
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        JSONObject data = new JSONObject(response.toString()).getJSONObject("data");

        ShippingFeeResponse res = new ShippingFeeResponse();
        res.setTotal(data.getInt("total"));
        res.setServiceFee(data.getInt("service_fee"));
        res.setInsuranceFee(data.optInt("insurance_fee", 0));
        res.setPickStationFee(data.optInt("pick_station_fee", 0));
        res.setCouponValue(data.optInt("coupon_value", 0));
        res.setR2sFee(data.optInt("r2s_fee", 0));
        res.setDocumentReturn(data.optInt("document_return", 0));
        res.setDoubleCheck(data.optInt("double_check", 0));
        res.setCodFee(data.optInt("cod_fee", 0));
        res.setPickRemoteAreasFee(data.optInt("pick_remote_areas_fee", 0));
        res.setDeliverRemoteAreasFee(data.optInt("deliver_remote_areas_fee", 0));
        res.setCodFailedFee(data.optInt("cod_failed_fee", 0));

        return res;
    }
}
