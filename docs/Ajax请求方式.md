##### Ajax请求方式

1、ajax json数据请求方式

```
  					$.ajax({
                            url: '请求地址',
                            type: '请求类型',
                            dataType: 'json',
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(key value方式的数据),
                            success: function (req) {
                                layer.msg(req.msg);
                            }
                        });
```

2、ajax form数据请求方式

```
  					$.ajax({
                            url: '请求地址',
                            type: '请求类型',
                            dataType: 'json',
                            contentType: '',
                            data: key value方式的数据,
                            success: function (req) {
                                layer.msg(req.msg);
                            }
                        });
```

