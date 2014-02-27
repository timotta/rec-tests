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

public class CfuInMemoryTwoModels {
	public static void main(String[] args) throws IOException, TasteException {
		File modelFile1 = new File(CfuInMemoryTwoModels.class.getResource("teste-50000.csv")
				.getPath());
		DataModel modelToGenerate = new FileDataModel(modelFile1);
		
		UserSimilarity similarity = new PearsonCorrelationSimilarity(modelToGenerate);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(10,
				similarity, modelToGenerate);
		
		File modelFile2 = new File(CfuInMemoryTwoModels.class.getResource("teste-1000.csv")
				.getPath());
		DataModel modelToRecommend = new FileDataModel(modelFile2);
		
		Recommender recommender = new GenericUserBasedRecommender(modelToRecommend,
				neighborhood, similarity);
		
		LongPrimitiveIterator x = modelToRecommend.getUserIDs();
		
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
