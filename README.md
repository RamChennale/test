Here are the Git commands which are being covered:
NOTE: 
[variable name] == origin

git config
git init
git clone
git add
git commit
git diff
git reset
git status
git rm
git log
git show
git tag
git branch
git checkout
git merge
git remote
git push
git pull
git stash
So, let's get started!

Git Commands

1. git config

Usage: git config --global user.name “[name]”  
Usage: git config --global user.email “[email address]”  
This command sets the author name and email address respectively to be used with your commits.

2. git init

Usage: git init [repository name]
This command is used to start a new repository.

3. git clone

Usage: git clone [url]  
This command is used to obtain a repository from an existing URL.

4. git add

Usage: git add [file]  
This command adds a file to the staging area.

5. git add *  

Usage: git add *  
This command adds one or more to the staging area.

6. git commit

Usage: git commit -m “[ Type in the commit message]”  
This command records or snapshots the file permanently in the version history.

7. git commit -a 

Usage: git commit -a  
This command commits any files you’ve added with the git add command and also commits any files you’ve changed since then.

8. git diff

Usage: git diff  
This command shows the file differences which are not yet staged.

9.git diff –staged 

Usage: git diff –staged 
This command shows the differences between the files in the staging area and the latest version present.

10. git diff [first branch] [second branch]  

Usage: git diff [first branch] [second branch]  
This command shows the differences between the two branches mentioned.

11. git reset

Usage: git reset [file]  
This command unstages the file, but it preserves the file contents.

12. Usage: git reset [commit]  
This command undoes all the commits after the specified commit and preserves the changes locally.

13. Usage: git reset –hard [commit]  
This command discards all history and goes back to the specified commit.

14. git status

Usage: git status  
This command lists all the files that have to be committed.

15. git rm

Usage: git rm [file]  
This command deletes the file from your working directory and stages the deletion.

16. git log

Usage: git log  
This command is used to list the version history for the current branch.

17. Usage: git log –follow[file]  

This command lists version history for a file, including the renaming of files also.

18. git show

Usage: git show [commit]  
This command shows the metadata and content changes of the specified commit.

19. git tag

Usage: git tag [commitID]  
This command is used to give tags to the specified commit.

20. git branch

Usage: git branch  
This command lists all the local branches in the current repository.

21. Usage: git branch [branch name]  

This command creates a new branch.

22. Usage: git branch -d [branch name]  

This command deletes the feature branch.

23. git checkout

Usage: git checkout [branch name]  
This command is used to switch from one branch to another.

24. Usage: git checkout -b [branch name]  

This command creates a new branch and also switches to it.

25. git merge

Usage: git merge [branch name]  
This command merges the specified branch’s history into the current branch.

26. git remote

Usage: git remote add origin [Remote Server Link]  
Usage: git remote add [variable name] [Remote Server Link]  
This command is used to connect your local repository to the remote server.

27. git push

Usage: git push [variable name] master  
This command sends the committed changes of master branch to your remote repository.

28. Usage: git push [variable name] [branch]  

This command sends the branch commits to your remote repository.

29. Usage: git push –all [variable name]  

This command pushes all branches to your remote repository.

30. Usage: git push [variable name] :[branch name]  

This command deletes a branch on your remote repository.

31. git pull

Usage: git pull [Repository Link]  
This command fetches and merges changes on the remote server to your working directory.

32. git stash

Usage: git stash save  
This command temporarily stores all the modified tracked files.

33. Usage: git stash pop  

This command restores the most recently stashed files.

34. Usage: git stash list  

This command lists all stashed changesets.

35. Usage: git stash drop  

This command discards the most recently stashed changeset.
