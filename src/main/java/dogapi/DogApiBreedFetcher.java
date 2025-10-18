package dogapi;

import java.io.IOException;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;


/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    // NOTE: DON't need a token! because the info is the same for anyone,
    // anyone can access

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     *
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) {

        // array list of subbreeds
        ArrayList<String> subbreeds = new ArrayList<>();

        // make request to get subbreeds from api
        // make a OkHttpClient object from imported package thing
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        // write the specifics of the request
        final Request request = new Request.Builder()
                // String.format is just a way to sub in strings where the %s symbol is
                .url(String.format("https://dog.ceo/api/breed/%s/list", breed))
                // get a token

                //.addHeader("content=type", "application/JSON")
                .build();

        try {
            // try calling and see if it works
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            // if the api call works, and thus the json thing says it is a success
            if (responseBody.get("status").equals("success")) {
                //"message" is the key used in the returned dict ./ JSON thing
                final JSONArray subbreedsArray = responseBody.getJSONArray("message");
                // add them all to my string (to manually convert from JSONArray to list of strings)
                for (Object subbreed : subbreedsArray) {
                    subbreeds.add(subbreed.toString());
                }
                return subbreeds;
            } else {
                throw new BreedNotFoundException(breed);
            }
        } catch (IOException | JSONException event) {
            throw new BreedNotFoundException(breed);

        }

    }
}