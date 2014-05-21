#!/bin/bash
#Meant to be used for running labs. At each step in the lab this should be run. It will optionally run the file .lab_check.sh
#and then do a Maven build. If the file .lab_post_check.sh exists this will also be run after Maven has built.
#If successful, it will stash away all of the users changes and advance to the next commit, until there are no more commits.
#The name of the current step can be specified in the file .current_step
#The first commit for the lab should be specified in the file .lab_start

if [ ! -f .lab_started ]
then
  echo "$(tput setaf 2)Initializing lab.$(tput sgr0)"
  first_commit=$(cat .lab_start)
  git reset --hard $first_commit
  currentStep=$(cat .current_step)
  touch .lab_started
  echo "$(tput setaf 2)Lab initialized to step $currentStep.$(tput sgr0)"
  exit
fi

currentStep=$(cat .current_step)
echo "$(tput setaf 2)Building lab step $currentStep.$(tput sgr0)"
build_passed=1
#check if we should run some extra steps besides the maven build
if [ -f .lab_check.sh ]
then
  sh .lab_check.sh
  [ $? -ne 0 ] && build_passed=0
fi
if [ $build_passed -eq 1 ]
then
  mvn clean install
  [ $? -ne 0 ] && build_passed=0
fi

if [ $build_passed -eq 1 ]
then
  if [ -f .lab_post_check.sh ]
  then
    sh .lab_post_check.sh
    [ $? -ne 0 ] && build_passed=0
  fi
fi


if [ $build_passed -eq 1 ]
then
  #move to the next child (should only be one, but we'll just take the first one listed if there are more)
  commit=$(git rev-parse HEAD | head -n1)
  child=$(git log --format='%H %P' --all | grep -F " $commit" | cut -f1 -d' ' | head -n1)
  if [ x$child == "x" ]
  then
    echo "$(tput setaf 2)The lab is complete, congratulations!$(tput sgr0)"
  else
    echo "$(tput setaf 2)Build passed, moving on to next step.$(tput sgr0)"
    git stash save --include-untracked "Lab stash step $currentStep"
    git reset --hard $child
  fi
else
  echo "$(tput setaf 1)Build didn't pass. Fix build and rerun this command.$(tput sgr0)"
fi