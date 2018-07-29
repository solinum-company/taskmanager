package config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import rugal.sample.core.repository.RepositoryPackage;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

/**
 * Java based application context configuration class.
 *
 * @author Rugal Bernstein
 * @since 0.2
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = RepositoryPackage.class)
@ComponentScan(value = "rugal.sample.core")
public class ApplicationContext extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "taskmanager";
	}

	@SuppressWarnings("deprecation")
	@Override
	@Bean
	public MongoClient mongo() throws Exception {
		List<ServerAddress> saList = new ArrayList<>();
		saList.add(new ServerAddress("cluster0-shard-00-00-4glbq.mongodb.net",
				27017));
		saList.add(new ServerAddress("cluster0-shard-00-01-4glbq.mongodb.net",
				27017));
		saList.add(new ServerAddress("cluster0-shard-00-02-4glbq.mongodb.net",
				27017));

		char[] pwd = "solinum_123".toCharArray();
		MongoCredential credential = MongoCredential.createCredential(
				"solinum_123", "admin", pwd);

		// set sslEnabled to true here
		MongoClientOptions options = MongoClientOptions.builder()
				.readPreference(ReadPreference.primaryPreferred())
				.retryWrites(true).requiredReplicaSetName("Cluster0-shard-0")
				.maxConnectionIdleTime(6000).sslEnabled(true).build();

		MongoClient mongoClient = new MongoClient(saList,
				Arrays.asList(credential), options);
		return mongoClient;
	}

	@Override
	protected String getMappingBasePackage() {
		return "rugal.sample.core.entity";
	}

}
