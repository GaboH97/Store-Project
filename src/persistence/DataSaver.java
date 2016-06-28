package persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import model.dao.Store;
import model.entity.Product;

public class DataSaver {

	public DataSaver() {

	}

	public static void SaveData(Store store) throws JsonSyntaxException, JsonIOException, IOException {
		FileWriter writer = new FileWriter("src/data/storeData.json");
		writer.write(new Gson().toJson(store));
		writer.flush();
		writer.close();
	}

	public void addProductToDataBase(Product product)
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		JsonObject mainObject = gson.fromJson(new BufferedReader(new FileReader("src/data/storeData.json")),
				JsonObject.class);
		JsonArray productsList = mainObject.get("productsList").getAsJsonArray();
		productsList.add(toJsonElement(product));
		System.out.println(productsList.toString());
		System.out.println(gson.toJson(product));
	}

	public static JsonElement toJsonElement(Object yaml) {
		if (yaml == null) {
			return JsonNull.INSTANCE;
		} else if (yaml instanceof List) {
			return toJsonArray((List<?>) yaml);
		} else if (yaml instanceof Map) {
			return toJsonObject((Map<?, ?>) yaml);
		} else {
			return new JsonPrimitive(yaml.toString());
		}
	}

	public static JsonArray toJsonArray(List<?> yaml) {
		JsonArray array = new JsonArray();
		for (Object o : yaml) {
			array.add(toJsonElement(o));
		}
		return array;
	}

	public static JsonObject toJsonObject(Map<?, ?> yaml) {
		JsonObject obj = new JsonObject();
		for (Map.Entry<?, ?> entry : yaml.entrySet()) {
			obj.add(entry.getKey().toString(), toJsonElement(entry.getValue()));
		}
		return obj;
	}

	public static void main(String[] args) {
		Store store = new Store();
		/*try {
			// store.addProduct(new Product(234, "23432",2342332,
			// Category.BOOKS, "dsfsd", 324));
			DataSaver dataSaver = new DataSaver();
			// DataSaver.SaveData(store);
			dataSaver.addProductToDataBase(new Product(234, "LAVERGA", 2342332, Category.BEAUTY, "COÑOOOO", 324));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
