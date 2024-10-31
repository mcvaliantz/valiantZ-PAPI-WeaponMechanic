package space.vishsiri.weaponmechanicpapi;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class WeaponMechanicPAPI extends JavaPlugin {

    private Placeholder placeholder;

    @Override
    public void onEnable() {
        saveDefaultConfig(); // Ensures that config.yml is created if it doesn't exist
        reloadConfig(); // Reloads the config.yml file

        // Load LICENSE_ID from config.yml
        FileConfiguration config = getConfig();
        String licenseId = config.getString("license_id", "YOUR_LICENSE_ID");

        if (checkLicense(licenseId)) {
            getLogger().info("License is valid. Plugin enabled.");
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                placeholder = new Placeholder(this);
                placeholder.register();
            }
        } else {
            getLogger().warning("Invalid or expired license. Plugin will be disabled.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (placeholder != null) {
            placeholder.unregister();
            placeholder = null;
        }
    }

    private boolean checkLicense(String licenseId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String WEB_APP_URL = "https://script.google.com/macros/s/AKfycbxE6tEXI7IdY511gNNNERP-qLMIkFKXIyIPCXxDnwnGsRJfTxitqH8aJxdhv1_drrGu/exec";
            HttpGet request = new HttpGet(WEB_APP_URL + "?action=checkLicense&licenseId=" + licenseId);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String result = EntityUtils.toString(response.getEntity());
                return "Valid".equals(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
