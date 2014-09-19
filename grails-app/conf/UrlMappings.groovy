class UrlMappings {

	static mappings = {
		
		"/me" (controller: "user") {
			action = [GET: "show"]
		}
		
		
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
