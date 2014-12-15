class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?(.$format)?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/me" (controller: "user") {
			action = [GET: "show"]
		}
		
		"/me/projects" (controller: "project") {
			action = [POST: "create",GET: "list"]
		}
		
		"/me/projects/$id" (controller: "project") {
			action = [GET: "get", DELETE: "delete"]
		}
		
		"/me/projects/$id/stages" (controller: "stage") {
			action = [POST: "create", GET: "list"]
		}
		
		"/me/projects/$id/stages/$stageId" (controller: "stage") {
			action = [GET: "get"]
		}
		
        "/"(view:"/index")
	}
}
