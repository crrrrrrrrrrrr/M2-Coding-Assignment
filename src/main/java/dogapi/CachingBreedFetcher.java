package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private HashMap<String, List<String>> cachedBreeds = new HashMap<>();
    BreedFetcher breedFetcher;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        breedFetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {



        // NEW: first check if the breed is already in the saved breeds
        if (cachedBreeds.containsKey(breed)) {
        }
        else {
            // NEW: increment the callsMade
            callsMade++;
            cachedBreeds.put(breed, breedFetcher.getSubBreeds(breed));
        }
        return cachedBreeds.get(breed);
    }


    public int getCallsMade() {
        return callsMade;
    }


}