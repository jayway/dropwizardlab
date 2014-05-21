/** @jsx React.DOM */

var Question = React.createClass({
    getInitialState: function() {
        return {expanded: false, bodyRequested:false};
    },
    handleClick: function(e) {
        this.setState({expanded: !this.state.expanded});
        if (!this.state.bodyRequested) {
            $.get("../questions/" + this.props.questionId, function(result) {
                this.setState({
                    bodyRequested: true,
                    body: result.body
                });
            }.bind(this));
        }
    },
    render: function() {
        var expanded = this.state.expanded;
        if (!expanded) {
            return ( <span onClick={this.handleClick}>{this.props.title}</span> );
        } else {
            return (
                <div onClick={this.handleClick}>
                    <span>{this.props.title}</span>
                    <div dangerouslySetInnerHTML={{__html: this.state.body}} />
                </div>
                );
        }
    }
});

var ReloadButton = React.createClass({
    handleClick: function (e) {
        this.props.clicked();
    },
    render: function () {
        return ( <button type="button" className="btn" onClick={this.handleClick}>Reload</button> );
    }
});

var QuestionList = React.createClass({
    getInitialState: function() {
        return {
            questions: []
        };
    },

    refresh: function() {
        $.get(this.props.source, function(result) {
            this.setState({
                questions: result
            });
        }.bind(this));
    },

    componentDidMount: function() {
        this.refresh();
    },

    render: function() {

        var questionItems = this.state.questions.map(function (question) {
            //parse HTML characters
            var questionText = jQuery.parseHTML(question.title).reduce(function (p, c){return p + c.data}, "");
            return (
                <li className="list-group-item"><Question title={questionText} questionId={question.id} /></li>
                );
        }, this);

        var reload = (<ReloadButton clicked={this.refresh} />);

        return (
            <div>
                {reload}
                <div className="row">
                    <div className="col-lg-12">
                        <ul>
                        {questionItems}
                        </ul>
                    </div>
                </div>
            </div>
            );
    }
});
