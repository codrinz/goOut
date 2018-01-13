import React from 'react';
import {StyleSheet, View, Text, Button, TextInput, FlatList, TouchableOpacity } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';

global.shows = [
  {
    key: "Show 1",
    day: 1
  },
  {
    key: "Show 2",
    day: 2
  }

]

const HomeScreen = ({ navigation }) => (
  <View style={styles.container}>
    <Button
    onPress={() => navigation.navigate('Shows')}
    title="Shows this week"
    style={styles.button}
     />
    <Button
      onPress={() => navigation.navigate('Contact')}
      title="Contact"
      style={styles.contactButton}
    />
  </View>
);

class ChangeDateScreen extends React.Component{
  static navigationOptions = {
    tabBarLabel: 'Change date',
  };
  constructor(props) {
    super(props);
    this.state = {key:"", day:0};
    var current_element = this.props.navigation.state.params;
    this.state.key = current_element.key;
    this.state.day = current_element.day;
}
render(){
  return(<View>
    <Text>{this.state.key}</Text>
    <Text>Old day {this.state.day}</Text>
    <TextInput onChangeText={(day) => this.setState({day})}
        value={this.state.day}/>
    </View>)
}
}

class ShowsScreen extends React.Component {
  static navigationOptions = {
    tabBarLabel: 'Shows this week',
  };
  constructor(props) {
    super(props);
}
render(){
  return(<FlatList
    data={global.shows}
    renderItem={({item}) =>
    <TouchableOpacity onPress = {()=> this.props.navigation.navigate("ChangeDate", item)}>
    <View style = {styles.listEntry}>
       <Text>{item.key}</Text>
       <Text>{item.day}</Text>
    </View>
    </TouchableOpacity>}
    
    />);
}
}

class ContactScreen extends React.Component {
	static navigationOptions = {
    tabBarLabel: 'Contact',
  };


  constructor(props) {
    super(props);
	this.state = {subject:"", body:""}
  }
  
  send_mail() {
	  Communications.email(["codrin.st26@gmail.com"],null, null, this.state.subject, this.state.body);
  }
	
  render() {
    return (
      <View style={styles.container}> 
		<Text>
		Subject
		</Text>
		<TextInput style={styles.subject} value={this.state.subject} onChangeText={(subject)=>this.setState({subject:subject, body:this.state.body})}>
		</TextInput>
		<Text>
			Message
		</Text>
		<TextInput style={styles.text} multiline={true} value={this.state.body} onChangeText={(body)=>this.setState({subject:this.state.subject, body:body})}>
		</TextInput>
		<Button style={styles.button} title="Send" onPress={this.send_mail.bind(this)}>
		</Button>
	  </View>
    );
  }
}

const RootNavigator = StackNavigator({
  Home: {
    screen: HomeScreen,
    navigationOptions: {
      headerTitle: 'Home',
    },
  },
  Contact: {
    screen: ContactScreen,
    navigationOptions: {
      headerTitle: 'Contact',
    },
  },
  Shows: {
    screen: ShowsScreen,
    navigationOptions: {
      headerTitle: 'Shows this week',
    },
  },
  ChangeDate:{
    screen: ChangeDateScreen,
    navigationOptions: {
      headerTitle: 'Change date of show',
    },
  }
});


const styles = StyleSheet.create({
  container: {
    paddingBottom: 10,
    flex: 2,
    flexDirection: 'column',
    justifyContent: 'space-between',

  },
  button: {
    flex: 1,
    color: '#000000',
    marginBottom: 50,
  },

  contactButton: {
    flex: 1,
    color: '#000000',
    justifyContent: 'flex-end',
    
  },

  subject: {
	  height:40
  },
  text: {
	  height: 250,
	  textAlignVertical: 'top'
  },

  listEntry:{
    marginBottom: 5,
    backgroundColor: '#8b0000'
  }
});
export default RootNavigator;
