# TypeScript

To compile the [TypeScript](https://www.typescriptlang.org/) files in this project, you need to install the [YARN Package Manager](https://yarnpkg.com/) on your system and then install the _TypeScript_ compiler.

## Install YARN Package Manager

On Ubuntu, execute the following command:
```
sudo apt install yarnpkg
```

## Install TypeScript Compiler

In the root folder of this project execute the following to install the _TypeScript_ compiler:
```
yarnpkg
```
It will download and install the compiler into the `node_modules` folder of this project.

## TypeScript Compilation

Eclipse is configured to automatically compile the _TypeScript_ files of this project into _Javascript_ code.

You can also trigger the _TypeScript_ compiler explicitly by executing this command in the root folder of this project:
```
./node_modules/.bin/tsc
```

## TypeScript Configuration

The _TypeScript_ compiler configuration can be found in the file `tsconfig.json`.