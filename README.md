# circle

## How to contribute (direct invites only)

> For better experience complete this steps before starting work for the first time:
> * Install GitKraken git client. All further steps would be described for this client.
> * Clone the repo
> * Set up the GitFlow: `File > Preferences > GitFlow > Initialize`

So, now you are ready to make your first commit! Let's start with getting latest changes. To do this:

1. Checkout the `develop` branch by clicking it twice
2. Perform `pull` to get changes from remote repo by selecting `Pull` button from top toolbar

Ok, now you have all cool stuff. Let's try to create something even better!

3. Create your feature branch by executing GitFlow command `Start > Feature`.
> Always name feature branches like `feature/my-cool-feature`.
>
>Please don't do it like `/my_cool_feature` or `/myCoolFeature`

4. Make your commits. As many as you want to!
5. If you want your branch to get newest changes from `develop`, you simply can `merge develop to feature/my-cool-feature` by pressing right mouse button on the latest commit from `develop`.
6. When you are ready to share your work with the world you should first `push` your feature branch (button in top toolbar).
7. Once push is done go on and `Create Pull Request` (left stacked menu, Pull Requests folder, green plus button on folder's header)

> To make pull request ready by pushing the green button `Create Pull Request`, please fill down the following fields first:
> - `From Repo` and `To Repo` with the same repo's name
> - `From Branch` with the name of your `feature/my-cool-feature`
> - `To Branch` with `develop`
> - `Title` with the name of your `my-cool-feature`
> - `Description` with some explanatory text

8. After creating pull request you can proceed to its page on github by pressing corresponding blue button
9. Wait for the owner to approve your changes
10. If all is ok (which I am absolutely sure of), you can end your feature by pressing Gitflow > `Finish my-cool-feature` green button. Bravely select `delete branch`.
11. Be ready to repeat this quest as many times as you want. Good luck!
