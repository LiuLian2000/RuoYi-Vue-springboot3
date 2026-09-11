-- 团队密码改为前端 RSA 加密后存储（密文），需加长字段以容纳 RSA-2048 密文（base64 约 344 字符）
ALTER TABLE password_manage_team
  MODIFY COLUMN team_password VARCHAR(512)
  COMMENT '团队密码（前端RSA加密密文入库，展示时前端解密；后端不再明文存储/比对）';
