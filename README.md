# iCurrency
A minecraft banking/economy mod that is per-server based and similar to real life.

# What can I do in this mod?
In this mod you can do practically anything you want to, the mod is evolving constantly and there will be some smaller and some bigger updates. Your balance and card balance are all stored on a database outside of minecraft for the mod to be tamper-proof, so you can rely on the mod to be used as your economy.

# How does the mod work?
Each player is assigned an uuid specific to only them and it has no correlation to the minecraft uuid. A player also needs to unlock their account when they go in game to be able to use the mod (withdraw, deposit etc..., cash will still work obviously). This mod is based on the real life economy (which does kinda suck) but it means you can build a strong economy in your minecraft server.

# How often will this mod be updated, and what will be added/changed with the updates?
The mod will be updated only when there is a content update or a bug fix, the bug fixes are less often because each release is tested on multiplayer and singleplayer before release. I do not know what will be added or changed in the future updates, but you will be able to see what was added/changed in the changelog.

# Why do you use a database instead of local files?
Well, for me using a database is easier than local files and also safer, if anyone gets the api to the database, its no biggie, I use security measures on the api to make sure no one can just spam the api with fake requests and give themselves infinite money or take someone else's money. After you unlock your account your minecraft client initiates a websocket to our server to receive confirmation codes and other stuff to make sure the request to the api is valid and not falsified. And in case someone does find a way to tamper with the API, we cannot do anything about that unless server owners or other people report it.

# How do I report API tampering?
Through our google form, located here: https://forms.gle/oUdvSCMDWE3VrmTy6
