# Code repository: https://github.com/GuyNitzan/Ad-Campaign-Spring-project

The app was developed with Spring, Hibernate, H2 in-memory DB (all data is cleared whenever the Spring app stops)

# When creating a new campaign:
	- Body parameter has to be a json with all 4 fields: name, bid, sellerId, category (category is not listed in the instructions!).
	- There has to be at least one product of the given category, by the given sellerId.
	- There can't be more than one campaign with the same category and sellerId.
	- If parameters are valid, a campaign will be created for the protucts of the given category by the given seller.

# when calling 'serve-ad' with a certain category:
	- Only a product of the given category will be selected
	- If there are no promoted products of the given category, a promoted product of another category will be selected.
	- If the campaign with the highest bid has a few products, The most expensive one will be selected (not in instructions)

# A campaign status can be changed from 'ACTIVE' to 'INACTIVE' (not 'DELETED' as stated in the instructions) and vice versa via a PATCH request
	- 'INACTIVE' campaigns are not considered when handling 'serve-ad' requests

# A campaign bid can also be changed via PATCH requests to change its ranking in the highest-bid campaigns rankings

# A campaign can be deleted via DELETE request

# A campaign or all campaigns can be received via GET requests (use the auto-generated id to get a single campaign)
