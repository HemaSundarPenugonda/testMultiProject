int execCount = params.testDataSize as int;
println "test data size is " + execCount

int lineNumber = params.lineNumber as int;
println "Initial line number is " + lineNumber

String executeAllTestData = params.executealltestdata;
String executeAllProjects = params.executeAllProjects;
String executeAllTestSuites = params.executeAllTestSuites;

node {
	stage('Preparation') {

		// Wipe the workspace so we are building completely clean
		//deleteDir()

		// Get backbase code from a bitbucket repository
		git credentialsId: '6d5f5ec8-3882-4af2-ae80-68a491f735ad', url: 'https://github.com/xebia/CoinAutomation.git', branch: 'master'
	}

	wrap([$class: 'Xvnc']) {
		if (executeAllTestData == "true") {
			int res = lineNumber
			stage('Build') {  sh 'mvn clean test -DLineNumberCustom='+res+' -Dexecutealltestdata='+executeAllTestData+ ' -Dexecuteallprojects='+executeAllProjects+' -DexecuteAllTestSuites='+executeAllTestSuites }
		} else {
			for (int i=1; i<=execCount; i++) {

				int res = lineNumber+(i-1)
				println "Current build's test data line number is " + res

				// Run the maven build
				stage('Build'+i) {  sh 'mvn clean test -DLineNumberCustom='+res+' -Dexecutealltestdata='+executeAllTestData+ ' -Dexecuteallprojects='+executeAllProjects+' -DexecuteAllTestSuites='+executeAllTestSuites }
			}
		}
		
	}

	stage('publish report') {
		publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'src/test/resources/HTML_Report', reportFiles: 'ExecutionReport.html', reportName: 'HTML Report'])
	}
}
