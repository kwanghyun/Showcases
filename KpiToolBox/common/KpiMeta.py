import MySQLdb

def getDatabase():
    # Open database connection
    db = MySQLdb.connect("localhost","root","root","test")
    return db
                    
def closeConnection(db):
    # disconnect from server
    db.close()

targetTables = [
'kpi_capacity_utilization_retained_period_vs_all',
'kpi_capcity_locker_package_count',
'kpi_capcity_locker_utilization_site_bank_vs_all',
'kpi_courier_dropoff_site_bank_vs_all',
'kpi_courier_pickup_site_bank_vs_all',
'kpi_courier_unproductive_dropoffs',
'kpi_customer_drop_off_site_bank_vs_all',
'kpi_customer_pickup_site_bank_vs_all',
'kpi_customer_redelivery_site_bank_wise',
'kpi_customer_retrieve_time_site_wise',
'kpi_customer_satisfaction_survey_site_bank_vs_all',
'kpi_ideal_config_all'
];


targetTablesWeekly = [
'kpi_capacity_utilization_retained_period_vs_all_weekly',
'kpi_capcity_locker_utilization_site_bank_vs_all_weekly',
'kpi_courier_dropoff_site_bank_vs_all_weekly',
'kpi_courier_pickup_site_bank_vs_all_weekly',
'kpi_customer_drop_off_site_bank_vs_all_weekly',
'kpi_customer_pickup_site_bank_vs_all_weekly',
'kpi_customer_satisfaction_survey_site_bank_vs_all_weekly',
];

targetTablesMonthly = [
'kpi_capacity_utilization_retained_period_vs_all_monthly',
'kpi_capcity_locker_utilization_site_bank_vs_all_monthly',
'kpi_courier_dropoff_site_bank_vs_all_monthly',
'kpi_courier_pickup_site_bank_vs_all_monthly',
'kpi_customer_drop_off_site_bank_vs_all_monthly',
'kpi_customer_pickup_site_bank_vs_all_monthly',
'kpi_customer_satisfaction_survey_site_bank_vs_all_monthly'
];

targetTablesYearly = [
'kpi_capacity_utilization_retained_period_vs_all_yearly',
'kpi_capcity_locker_utilization_site_bank_vs_all_yearly',
'kpi_courier_dropoff_site_bank_vs_all_yearly',
'kpi_courier_pickup_site_bank_vs_all_yearly',
'kpi_customer_drop_off_site_bank_vs_all_yearly',
'kpi_customer_pickup_site_bank_vs_all_yearly',
'kpi_customer_satisfaction_survey_site_bank_vs_all_yearly'
];

