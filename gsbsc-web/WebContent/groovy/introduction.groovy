def forward(page, req, res) {
	dis = req.getRequestDispatcher(page);
	dis.forward(req, res);
}
forward("introduction.gsp", request, response);
