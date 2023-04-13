#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20230223.sql ./mysql/db
cp ../sql/ry_config_20220929.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../yozu-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy yozu-gateway "
cp ../yozu-gateway/target/yozu-gateway.jar ./yozu/gateway/jar

echo "begin copy yozu-auth "
cp ../yozu-auth/target/yozu-auth.jar ./yozu/auth/jar

echo "begin copy yozu-visual "
cp ../yozu-visual/yozu-monitor/target/yozu-visual-monitor.jar  ./yozu/visual/monitor/jar

echo "begin copy yozu-modules-system "
cp ../yozu-modules/yozu-system/target/yozu-modules-system.jar ./yozu/modules/system/jar

echo "begin copy yozu-modules-file "
cp ../yozu-modules/yozu-file/target/yozu-modules-file.jar ./yozu/modules/file/jar

echo "begin copy yozu-modules-job "
cp ../yozu-modules/yozu-job/target/yozu-modules-job.jar ./yozu/modules/job/jar

echo "begin copy yozu-modules-gen "
cp ../yozu-modules/yozu-gen/target/yozu-modules-gen.jar ./yozu/modules/gen/jar

