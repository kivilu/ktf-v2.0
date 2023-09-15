ALTER TABLE `ktf_org_corp`
ADD COLUMN `ipk_domain`  varchar(255) NOT NULL DEFAULT 'test(sm2).cn' COMMENT 'IPK密钥域' AFTER `address`;
