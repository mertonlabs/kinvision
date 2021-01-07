# KInvision (WIP)

A basic Kotlin library for interacting with the [Invision REST API](https://invisioncommunity.com/developers/rest-api),
currently under development and used internally at Merton Labs. This library is built on top of
the [Ktor HTTP client library](https://ktor.io/docs/clients-index.html) using the Apache engine.

OAuth parts of the API are currently not supported (we don't have a need for them), but if you're interested in
contributing feel free to make a PR.

## License

Licensed under the EUPL v1.2.

For the text of the license in English, see [LICENSE.md](LICENSE.md).

For other languages, see https://joinup.ec.europa.eu/collection/eupl/eupl-text-11-12 

## Usage

In order to get started with KInvision, you need to create an API key in the admin panel of your Invision Community. You
can do so by going to https://yourcommunity.com/community/admin/?app=core&module=applications&controller=api&tab=apiKeys
or by finding the REST & OAuth section of the System pane.

Once you have your key, simply initialize the client as follows:

```val invision = KInvision.create(key = "<yourkey>", baseUrl = "https://mycommunity.com/")```

You can verify that the key and library are working by retrieving your community information:

```
val info = invision.getCommunityInfo()
println(info)
```

## Supported functionality

Not all features of the Invision API have been implemented yet, our primary focus has been around getting the Members
endpoints working.

### Members

- [X] Retrieve a Member by ID
- [X] Retrieve list of Members (with searching, sorting and paging)
- [X] Create a new Member
- [X] Update an existing Member
- [ ] Delete a Member
- [ ] Follow a Member
- [ ] Unfollow a Member
- [ ] Retrieve Members followed
- [ ] Retrieve Member notifications
- [ ] Retrieve Member warnings
- [ ] Give Member a warning
- [ ] Remove an existing warning
- [ ] Acknowledge a warning

### Groups

- [ ] Retrieve a Group by ID
- [ ] Retrieve list of Groups
- [ ] Delete a Group

