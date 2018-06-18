MEWORKBENCH_VERSION=1.0.3


#This variable must be iqual to id in distributionManagement.repository/snapshotRepository on pom.xml
DEPLOYMENT_REPO_ID="oss.sonatype.org"


echo DEPLOY_FLAG: $1
if [ $1 = "master" ]; then
	MEWORKBENCH_VERSION=$MEWORKBENCH_VERSION
else
	MEWORKBENCH_VERSION=${MEWORKBENCH_VERSION}-SNAPSHOT
fi
echo VERSION: $MEWORKBENCH_VERSION

#echo OSS_SONATYPE_USER $OSS_SONATYPE_USER
#echo OSS_SONATYPE_PASS $OSS_SONATYPE_PASS



H2_HOME=$HOME/.deploym2

echo ${H2_HOME}

M2REPOSITORY=$H2_HOME/repository
mkdir -p ${M2REPOSITORY}
cp build/oss.sonatype.settings.xml ${H2_HOME}/settings.xml

ls ${H2_HOME}

sed -i -e s,LOCAL_M2,$H2_HOME,g ${H2_HOME}/settings.xml
sed -i -e s/OSS_SONATYPE_USER/$OSS_SONATYPE_USER/g ${H2_HOME}/settings.xml
sed -i -e s/OSS_SONATYPE_PASS/$OSS_SONATYPE_PASS/g ${H2_HOME}/settings.xml
sed -i -e s/DEPLOYMENT_REPO_ID/$DEPLOYMENT_REPO_ID/g ${H2_HOME}/settings.xml

#GPG_PRIVATE_KEY=`cat build/gpg_private`
#echo GPG_PRIVATE_KEY: $GPG_PRIVATE_KEY
#gpg -v --batch --import <(echo "$GPG_PRIVATE_KEY")

mvn -Dmeworkbench.version=${MEWORKBENCH_VERSION} -s ${H2_HOME}/settings.xml -Dmaven.repo.local=$M2REPOSITORY deploy