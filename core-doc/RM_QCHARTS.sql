-- Remove QCHARTS table and data, 0.7.4 (RELEASE package) no need QCHARTS.

delete from tb_sys where SYS_ID = 'QCHARTS';

delete from tb_role_permission where PERMISSION like 'QCHARTS_%';

delete from tb_role_permission where PERMISSION like 'qcharts.%';

delete from tb_sys_menu where PROG_ID like 'QCHARTS_%';

delete from tb_sys_menu_role where PROG_ID like 'QCHARTS_%';

delete from tb_sys_multi_name where SYS_ID = 'QCHARTS';

delete from tb_sys_prog where PROG_SYSTEM='QCHARTS';

delete from tb_sys_prog_multi_name where PROG_ID like 'QCHARTS_%';

delete from tb_sys_ws_config where SYSTEM='QCHARTS';

delete from tb_sys_ctx_bean where SYSTEM='QCHARTS';

SET foreign_key_checks = 0;

DROP TABLE `qc_data_query`;
DROP TABLE `qc_data_query_mapper`;
DROP TABLE `qc_data_query_mapper_set`;
DROP TABLE `qc_data_source_conf`;
DROP TABLE `qc_data_source_driver`;
DROP TABLE `qc_olap_catalog`;
DROP TABLE `qc_olap_conf`;
DROP TABLE `qc_olap_mdx`;
DROP TABLE `qrtz_z_qcharts_blob_triggers`;
DROP TABLE `qrtz_z_qcharts_calendars`;
DROP TABLE `qrtz_z_qcharts_cron_triggers`;
DROP TABLE `qrtz_z_qcharts_fired_triggers`;
DROP TABLE `qrtz_z_qcharts_job_details`;
DROP TABLE `qrtz_z_qcharts_locks`;
DROP TABLE `qrtz_z_qcharts_paused_trigger_grps`;
DROP TABLE `qrtz_z_qcharts_scheduler_state`;
DROP TABLE `qrtz_z_qcharts_simple_triggers`;
DROP TABLE `qrtz_z_qcharts_simprop_triggers`;
DROP TABLE `qrtz_z_qcharts_triggers`;

SET foreign_key_checks = 1;
