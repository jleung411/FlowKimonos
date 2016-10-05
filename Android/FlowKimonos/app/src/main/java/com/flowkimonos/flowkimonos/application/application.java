package com.flowkimonos.flowkimonos.application;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.flowkimonos.flowkimonos.R;
import com.flowkimonos.flowkimonos.BuildConfig;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import com.shopify.buy.dataprovider.BuyClient;
import com.shopify.buy.dataprovider.BuyClientBuilder;
import com.shopify.buy.dataprovider.BuyClientError;
import com.shopify.buy.dataprovider.Callback;
import com.shopify.buy.model.Address;
import com.shopify.buy.model.Cart;
import com.shopify.buy.model.Checkout;
import com.shopify.buy.model.Collection;
import com.shopify.buy.model.CreditCard;
import com.shopify.buy.model.Customer;
import com.shopify.buy.model.LineItem;
import com.shopify.buy.model.PaymentToken;
import com.shopify.buy.model.Product;
import com.shopify.buy.model.ShippingRate;
import com.shopify.buy.model.Shop;
import com.shopify.buy.utils.AndroidPayHelper;

//import com.shopify.sample.ui.ProductDetailsBuilder;
//import com.shopify.sample.ui.ProductDetailsTheme;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;

public class application extends AppCompatActivity {

    private Firebase mRef;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
         */

        instance = this;

        initializeBuyClient();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*Action viewAction2 = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.flowkimonos.flowkimonos/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction2);
*/

        mRef = new Firebase("https://flow-kimonos.firebaseio.com/");
        mRef.child("flow-kimonos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }
            @Override public void onCancelled(FirebaseError error) { }
        });


    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.flowkimonos.flowkimonos/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);*/
        client.disconnect();
    }


    private static final String SHOP_PROPERTIES_INSTRUCTION =
            "\n\tAdd your shop credentials to a shop.properties file in the main app folder (e.g. 'app/shop.properties'). Include these keys:\n" +
                    "\t\tSHOP_DOMAIN=<myshop>.myshopify.com\n" +
                    "\t\tAPI_KEY=0123456789abcdefghijklmnopqrstuvw\n";

    private static application instance;

    private static Customer customer;

    public static BuyClient getBuyClient() {
        return instance.buyClient;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        application.customer = customer;
    }

    private BuyClient buyClient;
    private Checkout checkout;
    private PaymentToken paymentToken;
    private Shop shop;
/*
    private MaskedWallet maskedWallet;
*/
    public static final String ANDROID_PAY_FLOW = "com.shopify.sample.androidpayflow";

    // Use ENVIRONMENT_TEST for testing
    /*public static final int WALLET_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;
*/

    private void initializeBuyClient() {/*
        String shopUrl = BuildConfig.SHOP_DOMAIN;
        if (TextUtils.isEmpty(shopUrl)) {
            throw new IllegalArgumentException(SHOP_PROPERTIES_INSTRUCTION + "You must add 'SHOP_DOMAIN' entry in app/shop.properties, in the form '<myshop>.myshopify.com'");
        }

        String shopifyApiKey = BuildConfig.API_KEY;
        if (TextUtils.isEmpty(shopifyApiKey)) {
            throw new IllegalArgumentException(SHOP_PROPERTIES_INSTRUCTION + "You must populate the 'API_KEY' entry in app/shop.properties");
        }

        String shopifyAppId = BuildConfig.APP_ID;
        if (TextUtils.isEmpty(shopifyAppId)) {
            throw new IllegalArgumentException(SHOP_PROPERTIES_INSTRUCTION + "You must populate the 'APP_ID' entry in app/shop.properties");
        }
*/
        String applicationName = getPackageName();
/*
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(BuildConfig.OKHTTP_LOG_LEVEL);
*/
        /**
         * Create the BuyClient
         */
/*
        buyClient = new BuyClientBuilder()
                .shopDomain(shopUrl)
                .apiKey(shopifyApiKey)
                .appId(shopifyAppId)
                .applicationName(applicationName)
                .interceptors(logging)
                .networkRequestRetryPolicy(3, TimeUnit.MILLISECONDS.toMillis(200), 1.5f)
                .build();

        buyClient.getShop(new Callback<Shop>() {
            @Override
            public void success(Shop shop) {
                application.this.shop = shop;
            }

            @Override
            public void failure(BuyClientError error) {
                Toast.makeText(application.this, R.string.shop_error, Toast.LENGTH_LONG).show();
            }
        });*/
    }

    public void getCollections(final Callback<List<Collection>> callback) {
        buyClient.getCollections(1, callback);
    }


    public void getAllProducts(final int page, final List<Product> allProducts, final Callback<List<Product>> callback) {

        buyClient.getProducts(page, new Callback<List<Product>>() {
            @Override
            public void success(List<Product> products) {
                if (products.size() > 0) {
                    allProducts.addAll(products);
                    getAllProducts(page + 1, allProducts, callback);
                } else {
                    callback.success(allProducts);
                }
            }

            @Override
            public void failure(BuyClientError error) {
                callback.failure(error);
            }
        });
    }

    public void getProducts(Long collectionId, Callback<List<Product>> callback) {
        // For this sample app, we'll just fetch the first page of products in the collection
        buyClient.getProducts(1, collectionId, null, null, callback);
    }

    /**
     * Create a new checkout with the selected product. For convenience in the sample app we will hardcode the user's shipping address.
     * The shipping rates fetched in ShippingRateListActivity will be for this address.
     *
     * For the Android Pay Checkout, we will replace this with the address and email returned in the {@link MaskedWallet}
     *
     * @param product
     * @param callback
     */
    /*public void createCheckout(final Product product, final Callback<Checkout> callback) {
        Cart cart = new Cart();
        cart.addVariant(product.getVariants().get(0));

        checkout = new Checkout(cart);
        checkout.setWebReturnToLabel(getString(R.string.web_return_to_label));
        checkout.setWebReturnToUrl(getString(R.string.web_return_to_url));

        // if we have logged in customer use customer email instead of hardcoded one
        if (customer != null) {
            checkout.setEmail(customer.getEmail());
        } else {
            checkout.setEmail("something@somehost.com");
        }

        // the same for shipping address if we have logged in customer use customer default shipping address instead of hardcoded one
        if (customer != null && customer.getDefaultAddress() != null) {
            checkout.setShippingAddress(customer.getDefaultAddress());
            checkout.setBillingAddress(customer.getDefaultAddress());
        } else {
            final Address address = new Address();
            address.setFirstName("Dinosaur");
            address.setLastName("Banana");
            address.setAddress1("421 8th Ave");
            address.setCity("New York");
            address.setProvinceCode("NY");
            address.setZip("10001");
            address.setCountryCode("US");
            checkout.setShippingAddress(address);
            checkout.setBillingAddress(address);
        }

        checkout.setWebReturnToUrl(getString(R.string.web_return_to_url));
        checkout.setWebReturnToLabel(getString(R.string.web_return_to_label));

        buyClient.createCheckout(checkout, wrapCheckoutCallback(callback));
    }
**/
    public String getCartPermalink() {
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("http").path(buyClient.getShopDomain()).appendPath("cart");

        StringBuilder lineItemsStr = new StringBuilder();
        String prefix = "";
        for (LineItem lineItem : checkout.getLineItems()) {
            lineItemsStr.append(prefix);
            lineItemsStr.append(Long.toString(lineItem.getVariantId()));
            lineItemsStr.append(":");
            lineItemsStr.append(Long.toString(lineItem.getQuantity()));
            prefix = ",";
        }
        uri.appendPath(lineItemsStr.toString());

        uri.appendQueryParameter("channel", "mobile_app");
        uri.appendQueryParameter("checkout[email]", "email@domain.com");

        uri.appendQueryParameter("checkout[shipping_address][address1]", "Cart Permalink");
        uri.appendQueryParameter("checkout[shipping_address][city]", "Toronto");
        uri.appendQueryParameter("checkout[shipping_address][company]", "Shopify");
        uri.appendQueryParameter("checkout[shipping_address][first_name]", "Dinosaur");
        uri.appendQueryParameter("checkout[shipping_address][last_name]", "Banana");
        uri.appendQueryParameter("checkout[shipping_address][phone]", "416-555-1234");
        uri.appendQueryParameter("checkout[shipping_address][country]", "Canada");
        uri.appendQueryParameter("checkout[shipping_address][province]", "Ontario");
        uri.appendQueryParameter("checkout[shipping_address][zip]", "M5V2J4");

        return uri.build().toString();
    }

    /**
     * Update a checkout.
     */
    public void updateCheckout(final Checkout checkout, final Callback<Checkout> callback) {
        buyClient.updateCheckout(checkout, wrapCheckoutCallback(callback));
    }
/*
    public void updateCheckout(final Checkout checkout, MaskedWallet maskedWallet, final Callback<Checkout> callback) {
        // Update the checkout with the Address information in the Masked Wallet
        final Checkout updateCheckout = new Checkout(checkout.getToken());
        updateCheckout.setShippingAddress(AndroidPayHelper.createShopifyAddress(maskedWallet.getBuyerShippingAddress()));
        updateCheckout.setBillingAddress(AndroidPayHelper.createShopifyAddress(maskedWallet.getBuyerBillingAddress()));
        updateCheckout.setEmail(maskedWallet.getEmail());
        updateCheckout(updateCheckout, callback);
    }
*/
    public Checkout getCheckout() {
        return checkout;
    }

    public Shop getShop() {
        return shop;
    }
/*
    public MaskedWallet getMaskedWallet() {
        return maskedWallet;
    }

    public void setMaskedWallet(MaskedWallet maskedWallet) {
        this.maskedWallet = maskedWallet;
    }
*/
    public void getShippingRates(final Callback<List<ShippingRate>> callback) {
        buyClient.getShippingRates(checkout.getToken(), callback);
    }

    public void setShippingRate(ShippingRate shippingRate, final Callback<Checkout> callback) {
        checkout.setShippingRate(shippingRate);
        buyClient.updateCheckout(checkout, wrapCheckoutCallback(callback));
    }

    public void setDiscountCode(final String code, final Callback<Checkout> callback) {
        checkout.setDiscountCode(code);
        buyClient.updateCheckout(checkout, wrapCheckoutCallback(callback));
    }

    public void addGiftCard(final String code, final Callback<Checkout> callback) {
        buyClient.applyGiftCard(code, checkout, wrapCheckoutCallback(callback));
    }

    public void storeCreditCard(final CreditCard card, final Callback<PaymentToken> callback) {
        buyClient.storeCreditCard(card, checkout, new Callback<PaymentToken>() {
            @Override
            public void success(PaymentToken body) {
                application.this.paymentToken = body;
                callback.success(body);
            }

            @Override
            public void failure(BuyClientError error) {
                callback.failure(error);
            }
        });
    }

    public void completeCheckout(final Callback<Checkout> callback) {
        buyClient.completeCheckout(paymentToken, checkout.getToken(), wrapCheckoutCallback(callback));
    }
/*
    public void completeCheckout(FullWallet fullWallet, final Callback<Checkout> callback) {
        paymentToken = AndroidPayHelper.getAndroidPaymentToken(fullWallet, BuildConfig.ANDROID_PAY_PUBLIC_KEY);
        buyClient.completeCheckout(paymentToken, checkout.getToken(), wrapCheckoutCallback(callback));
    }

    public void launchProductDetailsActivity(Activity activity, Product product, ProductDetailsTheme theme) {
        ProductDetailsBuilder builder = new ProductDetailsBuilder(this, buyClient);
        Intent intent = builder.setShopDomain(buyClient.getShopDomain())
                .setProduct(product)
                .setTheme(theme)
                .setShop(shop)
                .build();
        activity.startActivityForResult(intent, 1);
    }
*/
    /**
     * Wraps the callbacks that are provided by the activities so that the checkout ivar is always up to date.
     *
     * @param callback
     * @return
     */
    private Callback<Checkout> wrapCheckoutCallback(final Callback<Checkout> callback) {
        return new Callback<Checkout>() {
            @Override
            public void success(Checkout checkout) {
                application.this.checkout = checkout;
                callback.success(checkout);
            }

            @Override
            public void failure(BuyClientError error) {
                callback.failure(error);
            }
        };
    }

}
