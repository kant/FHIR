---
layout: post
title:  Creating the fhir-validation assembly
description: Creating the fhir-validation assembly
date:   2019-10-08 09:59:05 -0400
permalink: /CreateFHIRValidationAssembly/
---

The instructions below can be used to create an assembly than contains all the jar files needed for someone trying to validate FHIR R4 spec compliance of a FHIR resource instance. Useful when you have a FHIR resource that was not created from the model or was recevied from an external system.

### Pre-Requisites

**Repository**  
Navigate to https://github.com/IBM/FHIR 

Open a terminal window 

Clone to a local working directory 
`git clone git@github.com:IBM/FHIR.git`

**Maven**  
You must have maven installed to create the build. https://maven.apache.org/ 
It must be in the `PATH`.

**Dependencies**  
The `fhir-validation` module requires the `fhir-examples` be installed prior. 

``` 
 mvn clean install -f fhir-examples/ 
 ```

You should see `[INFO] BUILD SUCCESS`, and are ready to proceed.

This installs the examples which are part of the build. 
 
### Build 
 To create the distribution, you can run the following profile. 
  
 ``` 
 mvn clean package -f fhir-validation/ -Pfhir-validation-distribution
 ```
 It'll create the following zip file - `fhir-validation/target/fhir-validation-distribution.zip` 

You should see `[INFO] BUILD SUCCESS`, and the assembly is ready.

This file contains: 
 

``` shell
14:05:17-paulbastide@pauls-mbp:~/git/wffh$ unzip -l ./tmp-fhir4/FHIR/fhir-validation/target/fhir-validation-distribution.zip
Archive:  ./tmp-fhir4/FHIR/fhir-validation/target/fhir-validation-distribution.zip
  Length      Date    Time    Name
---------  ---------- -----   ----
        0  10-18-2019 10:40   fhir-validation-dist/
   302248  09-06-2019 15:58   fhir-validation-dist/antlr4-runtime-4.5.3.jar
  6143682  10-17-2019 14:21   fhir-validation-dist/fhir-model-4.0.0-SNAPSHOT.jar
 10307984  10-17-2019 14:21   fhir-validation-dist/fhir-registry-4.0.0-SNAPSHOT.jar
    16922  10-17-2019 14:20   fhir-validation-dist/fhir-core-4.0.0-SNAPSHOT.jar
   140564  09-06-2019 15:58   fhir-validation-dist/jakarta.json-1.1.5.jar
    25058  09-11-2019 15:05   fhir-validation-dist/jakarta.annotation-api-1.3.5.jar
     2254  09-13-2019 12:39   fhir-validation-dist/jcip-annotations-1.0.jar
    21970  10-18-2019 10:40   fhir-validation-dist/fhir-validation-4.0.0-SNAPSHOT.jar
---------                     -------
 16960682                     9 files
```

# Download Dependencies
- FHIR [Download from BinTray](https://dl.bintray.com/ibm-watson-health/ibm-fhir-server-snapshots/com/ibm/fhir)
- Antlr [Antlr](https://repo1.maven.org/maven2/org/antlr/antlr4-runtime/4.5.3/)
- Json https://repo1.maven.org/maven2/jakarta/json/jakarta.json-api/1.1.5/
- Annotations https://repo1.maven.org/maven2/jakarta/annotation/jakarta.annotation-api/
- JCIP https://repo1.maven.org/maven2/net/jcip/jcip-annotations/1.0/
<p>
FHIR® is the registered trademark of HL7 and is used with the permission of HL7.
</p>
