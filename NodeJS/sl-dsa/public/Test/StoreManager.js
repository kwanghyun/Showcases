/*
 *	DBManager object to 
 */

// Locker Manager initialization...
var DBManager = (function(){

	var lockerdb;
	initDB();

	function initDB(){

		if (window.openDatabase) {

			// open db
			lockerdb = openDatabase("smartlockers", "0.1", "Locker database", 1024 * 1024);

			// create table "locker"
			lockerdb.transaction(function (t) {

				t.executeSql("DROP TABLE locker");
				t.executeSql("DROP TABLE staged");

				t.executeSql("CREATE TABLE IF NOT EXISTS locker (lockerId unique, size, orderNo, releaseCode, expiry, redelivery, version)");
				t.executeSql("CREATE TABLE IF NOT EXISTS staged (packageId unique, packageSize, expectedDepositDate, depositReleaseCode, pickupReleaseCode)");
			});

			// pre fill table "locker"
			lockerdb.transaction(function (t) {
				t.executeSql("INSERT OR IGNORE INTO locker (lockerId, size) VALUES (?, ?)", ["1", "MEDIUM"]);
				t.executeSql("INSERT OR IGNORE INTO locker (lockerId, size) VALUES (?, ?)", ["2", "MEDIUM"]);

				t.executeSql("INSERT OR IGNORE INTO staged (packageId, packageSize, pickupReleaseCode) VALUES (?, ?, ?)", ["111122223333", "SMALL", "AYVPK1"]);
				t.executeSql("INSERT OR IGNORE INTO staged (packageId, packageSize, pickupReleaseCode) VALUES (?, ?, ?)", ["111133335555", "MEDIUM", "AYVPK1"]);
			});

		} else {
			alert("WebSQL is not supported by your browser!");
		}
	}


	function updateLocker(data){
		console.log(data);

		console.log("UPDATE locker set orderNo = '"+data.orderNo+"' , releaseCode = 'AYVPK1' WHERE lockerId= '"+data.lockerId+"'");

		lockerdb.transaction(function (t) {
			t.executeSql("UPDATE locker set orderNo = '"+data.orderNo+"' , releaseCode = 'AYVPK1' WHERE lockerId= '"+data.lockerId+"'",
				[],
				function(){
					data.succ();
				}, function(){
					data.err();
				}
			);
		});

		/*lockerdb.transaction(function (t) {
			t.executeSql("UPDATE locker set orderNo = ? , releaseCode = ? WHERE lockerId= ? ", [data.orderNo, "AYVPK1", data.lockerId]);
		});*/
	}

	function insertStaged(order){
		if(order.packageId && order.depositReleaseCode && order.pickupReleaseCode){
			t.executeSql("INSERT INTO staged VALUES (?, ?, ? , ?, ?)", [order.packageId, order.lockerSize, order.expectedDepositDate, order.depositReleaseCode, order.pickupReleaseCode]);
		}
	}

	function getStagedOrder(packageId, succ){

		lockerdb.transaction(function (t) {
			t.executeSql("SELECT * FROM staged WHERE packageId = ? ", [packageId], function(t, results){
				console.log(results);
				if(results.rows.length){
					succ(results.rows[0]);
				}
			});
		});
	}

	function deleteStaged(packageId){
		lockerdb.transaction(function (t) {
			t.executeSql("DELETE FROM staged WHERE packageId = ?", [packageId], function(){}, function (transaction, error) {
				alert("Error : " + error.message + " in ");
			});
		});
	}

	function availableLockers(succ){
		lockerdb.transaction(function (t) {
			t.executeSql("SELECT * FROM locker WHERE orderNo is null", [], function(t, results){

				var lockers = [];

				for(var i=0; i<results.rows.length;i++){
					lockers.push({
						label: results.rows[i].lockerId,
						size: results.rows[i].size
					});
				}

				succ(lockers);
			});
		});
	}

	function deleteLockerOrder(lockerId){
		lockerdb.transaction(function (t) {
			t.executeSql("UPDATE locker set orderNo = '' , releaseCode = '' WHERE lockerId= ? ", [lockerId]);
		});
	}

	function deleteOrder(orderNo){
		lockerdb.transaction(function (t) {
			t.executeSql("DELETE FROM staged WHERE orderNo = ?", [orderNo]);
		});
	}

	function tracking(orderNo, succ, err){

		lockerdb.transaction(function (t) {
			t.executeSql("SELECT * FROM locker WHERE orderNo LIKE '%"+orderNo+"'", [], function(transaction, results){

				if(results.rows.length){
					succ(results.rows[0]);
				}else{
					err();
				}

			}, function (transaction, error) {
				alert("Error : " + error.message + " in ");
			});
		});

	}

	function release(orderNo, releaseCode, succ, err){
		lockerdb.transaction(function (t) {
			t.executeSql("SELECT * FROM locker WHERE orderNo LIKE '%"+orderNo+"' AND releaseCode = ? ", [releaseCode], function(transaction, results){

				if(results.rows.length){
					succ(results.rows[0]);
				}else{
					err();
				}

			}, function (transaction, error) {
				alert("Error : " + error.message + " in ");
			});
		});
	}

	return {

		dropOrder: function(obj){
			updateLocker(obj);
			deleteStaged(obj.orderNo);
		},

		pickupOrder: function(lockerId){
			deleteLockerOrder(lockerId);
		},

		insertStagedOrders: function(staged){
			if(staged && staged.length){
				for(var i=0;i<staged.length;i++){
					insertStaged(staged[i]);
				}
			}
		},
		getStagedOrder: function(packageId, succ){
			getStagedOrder(packageId, succ);
		},
		validateOrder: function(orderNo, succ, err){
			tracking(orderNo, succ, err);
		},

		validateRelease: function(orderNo, releaseCode, succ, err){
			release(orderNo, releaseCode, succ, err);
		},


		getAvailableLockers: function(succ){
			availableLockers(succ);
		},

		getLockerByOrder: function(orderNo){
			lockerdb.transaction(function (t) {
				t.executeSql("SELECT * FROM locker WHERE orderNo LIKE '%"+orderNo+"'", [], function(transaction, results){

					console.log(results);
					var id = results.rows[0].lockerId;
					$("#doorLabel").html(id);
					COM.SMARTLOCKER.App.lockerManager.open(id);

				});
			});
		}
	};

})();