package timotta.cfu;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class CfuInMemory1 {
	public static void main(String[] args) throws IOException, TasteException {
		File modelFile = new File(CfuInMemory1.class.getResource("teste1.csv")
				.getPath());
		DataModel model = new FileDataModel(modelFile);
		
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(10,
				similarity, model);
		Recommender recommender = new GenericUserBasedRecommender(model,
				neighborhood, similarity);
		
		LongPrimitiveIterator x = model.getUserIDs();
		
		while(x.hasNext()) {
			long userId = x.nextLong();
			List<RecommendedItem> recommendations = recommender.recommend(userId, 10);
			System.out.println("recomend to " + userId);
			for (RecommendedItem recommendation : recommendations) {
				System.out.println(" - " + recommendation);
			}
		}
	}
}
