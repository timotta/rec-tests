package timotta.cfu;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class CfuEvaluator {
	public static void main(String[] args) throws IOException, TasteException {
		File modelFile = new File(CfuEvaluator.class.getResource("teste-200000.csv")
				.getPath());
		DataModel dataModel = new FileDataModel(modelFile);

		final UserSimilarity similarity = new PearsonCorrelationSimilarity(
				dataModel);
		final UserNeighborhood neighborhood = new NearestNUserNeighborhood(10,
				similarity, dataModel);

		RecommenderBuilder builder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel trainingModel)
					throws TasteException {
				return new GenericUserBasedRecommender(trainingModel,
						neighborhood, similarity);
			}
		};

		DataModelBuilder dataModelBuilder = new DataModelBuilder() {
			public DataModel buildDataModel(
					FastByIDMap<PreferenceArray> trainingData) {
				return new GenericDataModel(trainingData);
			}
		};

		AverageAbsoluteDifferenceRecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		double quality = evaluator.evaluate(builder, dataModelBuilder,
				dataModel, 0.9, 0.9);

		System.out.println(quality);
	}
}
