import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your API key
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount in USD: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter the target currency (e.g., EUR, INR, GBP): ");
        String targetCurrency = scanner.next().toUpperCase();

        try {
            double convertedAmount = convertCurrency(amount, targetCurrency);
            System.out.println(amount + " USD is equivalent to " + convertedAmount + " " + targetCurrency);
        } catch (IOException e) {
            System.out.println("Error fetching conversion rate: " + e.getMessage());
        }
    }

    private static double convertCurrency(double amount, String targetCurrency) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");
        double rate = conversionRates.get(targetCurrency).getAsDouble();

        return amount * rate;
    }
}
