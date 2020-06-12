create or replace function create_constraint_if_not_exists ( t_name text, c_name text, constraint_sql text ) returns void AS $$ begin if not exists (SELECT conname FROM pg_constraint where conname = c_name) then execute constraint_sql; end if; end; $$ language 'plpgsql'; 
--SELECT create_constraint_if_not_exists( 'datatype_unite_variable_vdt', 'dvu_var_fk', 'ALTER TABLE datatype_unite_variable_snot_vdt ADD CONSTRAINT dvu_var_fk FOREIGN KEY (uni_id) REFERENCES variable_snot(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;'); 
-- SELECT create_constraint_if_not_exists( 'datatype_unite_variable_vdt', 'dvu_uni_fk', 'ALTER TABLE datatype_unite_variable_snot_vdt ADD CONSTRAINT dvu_uni_fk FOREIGN KEY (uni_id) REFERENCES unite(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;'); 
-- SELECT create_constraint_if_not_exists( 'datatype_unite_variable_vdt', 'dvu_dty_fk', 'ALTER TABLE datatype_unite_variable_snot_vdt ADD CONSTRAINT dvu_dty_fk FOREIGN KEY (dty_id) REFERENCES datatype(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;'); 
--SELECT create_constraint_if_not_exists( 'insertion_dataset_ids', 'ids_dtn_fk', 'ALTER TABLE insertion_dataset_ids ADD CONSTRAINT ids_dtn_fk FOREIGN KEY (dtn_id) REFERENCES datatype_unite_variable_snot_vdt(vdt_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;'); 
--SELECT create_constraint_if_not_exists( 'site_theme_datatype_variable', 'stdv_std_FK', 'ALTER TABLE site_theme_datatype_variable ADD CONSTRAINT "stdv_std_FK" FOREIGN KEY (dtn_id) REFERENCES site_theme_datatype (dtn_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION; '); 
--SELECT create_constraint_if_not_exists( 'site_theme_datatype_variable', 'stdv_var_FK', 'ALTER TABLE site_theme_datatype_variable ADD CONSTRAINT "stdv_var_FK" FOREIGN KEY (var_id) REFERENCES variable_snot (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION; '); 