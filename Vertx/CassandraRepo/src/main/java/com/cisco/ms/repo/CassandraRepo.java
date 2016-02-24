package com.cisco.ms.repo;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.RoundRobinPolicy;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import io.vertx.core.AbstractVerticle;
import org.apache.log4j.Logger;
import rx.Observable;


/**
 * Created by kwjang on 2/17/16.
 */
public class CassandraRepo extends AbstractVerticle {

    final static private Logger logger = Logger.getLogger(CassandraRepo.class);

    Cluster cluster;
    Session session;
    ResultSet results;
    Row rows;


//    @Override
//    public void start() throws Exception {
//        // Connect to the cluster and keyspace "demo"
//        cluster = Cluster.builder()
//                .addContactPoint("10.106.9.157")
//                .withRetryPolicy(DefaultRetryPolicy.INSTANCE)
//                .withLoadBalancingPolicy(new RoundRobinPolicy())
//                .build();
//        session = cluster.connect("event_ks");
////        insertUser();
//        selectAsync();
//    }

    @Override
    public void start() throws Exception {
        // Connect to the cluster and keyspace "demo"

        Observable<Cluster> observableCluster =
                Observable.just(cluster = Cluster.builder()
                        .addContactPoint("10.106.9.157")
                        .withRetryPolicy(DefaultRetryPolicy.INSTANCE)
                        .withLoadBalancingPolicy(new RoundRobinPolicy())
                        .build());
        observableCluster.subscribe(_cluster ->{
            Observable<Session> observableSession =
                    Observable.just(cluster.connect("event_ks"));
            observableSession.subscribe(_session -> {
                session = _session;
                selectAsync();
//                dropUserTable();
                createUserTable();
            });
        });
    }


    public void createUserTable(){
        ResultSetFuture future = session.executeAsync(
                "CREATE TABLE users (" +
                "  user_name varchar PRIMARY KEY," +
                "  age int," +
                "  gender varchar," +
                "  email varchar," +
                "  first_name varchar," +
                "  birth_year bigint" +
                ");");
        Futures.addCallback(future,
                new FutureCallback<ResultSet>() {
                    @Override
                    public void onSuccess(ResultSet result) {
                        logger.info("Create table " + result.toString());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        logger.info("Error while rCreate table : " + t.getMessage());
                    }
                }, MoreExecutors.sameThreadExecutor()
        );
    }

    public void insertUser() {
        // Insert one record into the users table
        PreparedStatement statement = session.prepare(
                "INSERT INTO users" + "(user_name, age, gender, email, first_name, birth_year)"
                        + "VALUES (?,?,?,?,?,?);");

        BoundStatement boundStatement = new BoundStatement(statement);

        session.execute(boundStatement.bind("testuser", 35, "M",
                "testuser@example.com", "Test", 1980L));
    }

    public void selectAsync() {
        ResultSetFuture future = session.executeAsync("SELECT release_version FROM system.local");
        Futures.addCallback(future,
                new FutureCallback<ResultSet>() {
                    @Override
                    public void onSuccess(ResultSet result) {
                        logger.info("Cassandra version is " + result.one().getString("release_version"));

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        logger.info("Error while reading Cassandra version: " + t.getMessage());
                    }
                }, MoreExecutors.sameThreadExecutor()
        );
    }

    public void dropUserTable(){
        Observable<ResultSetFuture> future = Observable.just(
                session.executeAsync("DROP Table event_ks.users"));
        future.subscribe(System.out::println);
    }
}
