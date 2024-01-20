import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class EkoloPayIntegration {

    public static void main(String[] args) {
        createPurchaseToken();
    }

    public static void createPurchaseToken() {
        try {
            String apiUrl = "https://ekolopay.com/api/v1/gateway/purchase-token";
            String apiClient = "j_dore_ngoh";
            String secretKey = "ec38adba-ece8-4049-ae3c-6626f2502b51";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("api_client", apiClient);
            parameters.put("amount", 150000);

            Map<String, Object> product = new HashMap<>();
            product.put("label", "Montant de la commande");
            product.put("amount", 150000);
            product.put("details", "Détails de la commande");
            parameters.put("product", product);

            Map<String, Object> customer = new HashMap<>();
            customer.put("uuid", "UUID du client");
            customer.put("name", "Nom du client");
            customer.put("phone", "Téléphone du client");
            parameters.put("customer", customer);

            parameters.put("secret_key", secretKey);

            // Convert parameters to URL-encoded format
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : parameters.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            // Create connection
            URL url = new URL(apiUrl + "?api_client=" + apiClient);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Write data to the connection
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.toString().getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // Get the response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println(response.toString());
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
