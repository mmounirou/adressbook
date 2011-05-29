$(document).ready(function()
{
	$("#cgroup").autocomplete(
	{
		source:function(request,response)
		{
			$.ajax(
				{
					url:"groups",
					dataType: "json",
					data:
					{
						term: request.term
					},
					success: function(data)
					{
						response($.map(data,function(item)
							{
								return {label: item.name , value: item.name}
							}
						));
					}
				});
		}
	});
});

$(document).ready(function()
{
	$('#list_contacts tr').click(function() {
		
		if($(this).find("a").size() != 0 )
		{
			
	  		window.location.href = $(this).find("a").attr("href");
		}
	});
});