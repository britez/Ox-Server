class UrlMappings {

	static mappings = {
		
		"/me" (controller: "user") {
			action = [GET: "show"]
		}
		
		"/me/projects" (controller: "project") {
			action = [POST: "create",GET: "list"]
		}
		
		"/me/projects/$id" (controller: "project") {
			action = [GET: "get", DELETE: "delete"]
		}
		
		
        "/"(view:"/index")
	}
}
