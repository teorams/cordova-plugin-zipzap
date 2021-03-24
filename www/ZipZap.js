var exec = require('cordova/exec');

function isNullOrWhitespace(text) {
    return !text || !text.replace(/\s/g, '').length;
}

function validateConfig(config, mode, callback) {
    if (typeof (config) !== 'object') {
        return callback('config is required and must be an object');
    }
    if (typeof(config.from) !== 'string' || isNullOrWhitespace(config.from)) {
        return callback('config.from is required and must be a string');
    }
    if (typeof(config.to) !== 'string' || isNullOrWhitespace(config.to)) {
        return callback('config.to is required and must be a string');
    }
    if (typeof(config.password) !== 'string' || isNullOrWhitespace(config.password)) {
        config.password = '';
    }

    if (mode === 'unzip') {
        window.resolveLocalFileSystemURL(config.from, function (result) {
            if (!result.isFile) {
                return callback('config.from must be a file url');
            }
            callback();
        }, function (error) {
            callback('failed while locating : ' + config.from + " (" + JSON.stringify(error) + ")");
        });
    }
}


exports.unzip = function (config, successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'ZipZap', 'unzip', [config.from, config.to, config.password]);
};


